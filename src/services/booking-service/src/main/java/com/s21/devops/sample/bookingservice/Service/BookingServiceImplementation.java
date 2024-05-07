package com.s21.devops.sample.bookingservice.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.s21.devops.sample.bookingservice.Communication.*;
import com.s21.devops.sample.bookingservice.Communication.Aux.DayAvailability;
import com.s21.devops.sample.bookingservice.Communication.Aux.RoomFilling;
import com.s21.devops.sample.bookingservice.Exception.*;
import com.s21.devops.sample.bookingservice.Model.Reservation;
import com.s21.devops.sample.bookingservice.Repository.ReservationRepository;
import com.s21.devops.sample.bookingservice.Statistics.QueueProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.UUID;

@Service
public class BookingServiceImplementation implements BookingService {
    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private HotelService hotelService;

    @Autowired
    private PaymentsService paymentsService;

    @Autowired
    private LoyaltyService loyaltyService;

    @Autowired
    private QueueProducer queueProducer;

    @Override
    public void patchHotelInfo(UUID hotelUid, PatchRoomsInfoReq patchRoomsInfoReq)
            throws JsonProcessingException {
        for (RoomFilling room:patchRoomsInfoReq.getRooms()) {
            String currentDateString = room.getInterval().getFrom();
            String lastDateString = room.getInterval().getTo();
            LocalDate current = LocalDate.parse(currentDateString.replace("T", " "),
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS"));
            LocalDate last = LocalDate.parse(lastDateString.replace("T", " "),
                    DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS"));

            while (current.isBefore(last)) {
                Reservation reservation = new Reservation();
                reservation.setCost(0.0f);
                reservation.setReservationUid(UUID.randomUUID());
                reservation.setDate(current);
                reservation.setHotelUid(hotelUid);
                current = current.plusDays(1);

                BookingStatisticsMessage bookingStatisticsMessage = new BookingStatisticsMessage();
                bookingStatisticsMessage.setDate(current.toString());
                bookingStatisticsMessage.setTimestamp(LocalDate.now().toString());
                bookingStatisticsMessage.setReservationUid(reservation.getReservationUid());
                bookingStatisticsMessage.setHotelUid(reservation.getHotelUid());
                if (room.getStatus().equals("reserved")){
                    bookingStatisticsMessage.setReserved(true);
                    reservationRepository.save(reservation);
                } else {
                    bookingStatisticsMessage.setReserved(false);
                    Iterable<Reservation> reservations =
                            reservationRepository.findAllByHotelUidAndUserUid(hotelUid, null);
                    if(reservations.iterator().hasNext()) {
                        reservationRepository.delete(reservations.iterator().next());
                    }
                }
                queueProducer.putStatistics(bookingStatisticsMessage);
            }
        }
    }

    @Override
    public void bookHotel(BookHotelReq bookHotelReq)
            throws ReservationAlreadyExistsException, CustomJwtException, CustomRuntimeException,
            PaymentNotFoundException, NoPaymentException, JsonProcessingException, CoudntPayException {
        String currentDateString = bookHotelReq.getDateInterval().getFrom();
        String lastDateString = bookHotelReq.getDateInterval().getTo();
        LocalDate current = LocalDate.parse(currentDateString.replace("T", " "),
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS"));
        LocalDate last = LocalDate.parse(lastDateString.replace("T", " "),
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS"));

        ArrayList<UUID> paymentUids = new ArrayList<>();
        ArrayList<BookingStatisticsMessage> bookingStatisticsMessages = new ArrayList<>();
        while (current.isBefore(last)){
            BookingStatisticsMessage bookingStatisticsMessage = new BookingStatisticsMessage();

            Reservation reservation = BookHotelReq.reservationFromBookHotelReq(bookHotelReq, current);
            reservation.setReservationUid(UUID.randomUUID());
            reservation.setUserUid(bookHotelReq.getUserUid());
            reservation.setCost(bookHotelReq.getRoomCost());

            bookingStatisticsMessage.setUserUid(bookHotelReq.getUserUid());
            bookingStatisticsMessage.setDate(current.toString());
            bookingStatisticsMessage.setTimestamp(LocalDate.now().toString());
            bookingStatisticsMessage.setHotelUid(bookHotelReq.getHotelUid());
            bookingStatisticsMessage.setReservationUid(reservation.getReservationUid());
            bookingStatisticsMessage.setReserved(true);
            queueProducer.putStatistics(bookingStatisticsMessage);
            bookingStatisticsMessages.add(bookingStatisticsMessage);

            LoyaltyBalanceRes loyaltyBalanceRes;
            double cost = bookHotelReq.getRoomCost();
            try {
                loyaltyBalanceRes = loyaltyService.getLoyaltyBalance(bookHotelReq.getUserUid());
            } catch (LoyaltyNotFoundException ex) {
                loyaltyBalanceRes = LoyaltyBalanceRes.LoyaltyBalanceResFromParams("NO", 0.0);
            }
            cost -= cost * loyaltyBalanceRes.getDiscount() / 100.0;
            loyaltyService.chargeBalance(bookHotelReq.getUserUid(), ChargeBalanceReq.chargeBalanceReqFromCharge((float)cost));

            try {
                UUID uuid = paymentsService.pay(PayReq.payReqFromParams(bookHotelReq.getPaymentInfo(), (float)cost));
                paymentUids.add(uuid);
                reservation.setPaymentUid(uuid);
            } catch (NoPaymentException ex) {
                fallbackPay(paymentUids, bookingStatisticsMessages);
                throw new CoudntPayException("Payment error!");
            }
            try {
                reservationRepository.save(reservation);
            } catch (Exception ex) {
                fallbackPay(paymentUids, bookingStatisticsMessages);
                throw new ReservationAlreadyExistsException("Reservation for date " + current.toString() + " already exists!");
            }
            current = current.plusDays(1);
        }
    }

    @Transactional
    @Override
    public void removeBooking(UUID hotelUid, UUID userUid)
            throws NoPaymentException, CustomJwtException, CustomRuntimeException, PaymentNotFoundException, JsonProcessingException {
        Iterable<Reservation> reservations = reservationRepository.findAllByHotelUidAndUserUid(hotelUid, userUid);
        for (Reservation res: reservations) {
            BookingStatisticsMessage bookingStatisticsMessage = new BookingStatisticsMessage();

            bookingStatisticsMessage.setUserUid(userUid);
            bookingStatisticsMessage.setDate(res.getDate().toString());
            bookingStatisticsMessage.setTimestamp(LocalDate.now().toString());
            bookingStatisticsMessage.setHotelUid(res.getHotelUid());
            bookingStatisticsMessage.setReservationUid(res.getReservationUid());
            bookingStatisticsMessage.setReserved(false);
            queueProducer.putStatistics(bookingStatisticsMessage);

            paymentsService.refund(res.getPaymentUid());
        }
        reservationRepository.deleteAllByHotelUidAndUserUid(hotelUid, userUid);
    }

    @Override
    public BookingInfo getBookingInfo(UUID hotelUid, UUID userUid)
            throws ReservationNotFoundException, CustomJwtException, HotelNotFoundException, CustomRuntimeException {
        Iterable<Reservation> reservations = reservationRepository.findAllByHotelUidAndUserUid(hotelUid, userUid);

        HotelInfoRes hotelInfoRes = hotelService.getHotel(hotelUid);
        Float cost = 0.0f;

        LocalDate from = LocalDate.now(), to = LocalDate.now();
        boolean first = true;
        for (Reservation res:reservations) {
            if (first){
                first = false;
                from = res.getDate();
                to = res.getDate();
            } else {
                if (res.getDate().isBefore(from)) {
                    from = res.getDate();
                }
                if (res.getDate().isAfter(to)){
                    to = res.getDate();
                }
            }
            cost += res.getCost();
        }
        if (first) {
            throw new ReservationNotFoundException("Reservation for hotel " + hotelUid + " was not found");
        }
        return BookingInfo.bookingInfoFromFields(hotelUid, userUid, from, to, hotelInfoRes.getName(), cost);
    }

    @Override
    public Iterable<BookingInfo> getAllBookingInfo(UUID userUid)
            throws ReservationNotFoundException, CustomJwtException, HotelNotFoundException, CustomRuntimeException {
        Iterable<Reservation> reservations = reservationRepository.findAllByUserUid(userUid);
        ArrayList<UUID> hotels = new ArrayList<>();
        ArrayList<BookingInfo> bookingInfos = new ArrayList<>();
        for (Reservation res:reservations) {
            if (!hotels.contains(res.getHotelUid())) {
                hotels.add(res.getHotelUid());
            }
        }
        for (UUID hoteUid:hotels) {
            bookingInfos.add(getBookingInfo(hoteUid, userUid));
        }
        return bookingInfos;
    }

    @Override
    public HotelsAavailabilityRes getHotelsAvailaibility(UUID hotelUid, String from,  String to)
            throws CustomJwtException, HotelNotFoundException, CustomRuntimeException {
        Iterable<Reservation> reservations = reservationRepository.findAllByHotelUid(hotelUid);

        LocalDate current = LocalDate.parse(from.replace("T", " "),
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS"));
        LocalDate last = LocalDate.parse(to.replace("T", " "),
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS"));

        ArrayList<DayAvailability> dayAvailabilities = new ArrayList<>();

        while (current.isBefore(last)){
            Integer currentCount = 0;
            for (Reservation res:reservations) {
                if (res.getDate().getDayOfYear() - current.getDayOfYear() == 0) {
                    currentCount += 1;
                }
            }
            HotelCapacityRes hotelCapacityRes = hotelService.getHotelCapacity(hotelUid);
            if (hotelCapacityRes.getRooms() <= currentCount){
                dayAvailabilities.add(DayAvailability.dayAvailabilityFromDate(current.toString(), false));
            } else {
                dayAvailabilities.add(DayAvailability.dayAvailabilityFromDate(current.toString(), true));
            }

            current = current.plusDays(1);
        }

        return HotelsAavailabilityRes.hotelsAavailabilityResFromArray(dayAvailabilities);
    }

    private void fallbackPay(ArrayList<UUID> paymentUids, ArrayList<BookingStatisticsMessage> bookingStatisticsMessages)
            throws NoPaymentException, CustomJwtException, CustomRuntimeException, PaymentNotFoundException, JsonProcessingException {
        for (UUID pUid:paymentUids) {
            paymentsService.refund(pUid);
        }
        for (BookingStatisticsMessage bsm:bookingStatisticsMessages) {
            bsm.setReserved(false);
            queueProducer.putStatistics(bsm);
        }
    }
}
