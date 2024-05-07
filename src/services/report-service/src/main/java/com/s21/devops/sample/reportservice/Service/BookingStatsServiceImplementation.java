package com.s21.devops.sample.reportservice.Service;

import com.s21.devops.sample.reportservice.Communication.BookingStatisticsMessage;
import com.s21.devops.sample.reportservice.Communication.HotelFillingStatistics;
import com.s21.devops.sample.reportservice.Model.BookingStatistics;
import com.s21.devops.sample.reportservice.Repository.BookingStatsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

@Service
public class BookingStatsServiceImplementation implements BookingStatsService {
    @Autowired
    private BookingStatsRepository bookingStatsRepository;

    @Override
    public void postBookingStatsMessage(BookingStatisticsMessage bookingStatisticsMessage) {
        if (bookingStatisticsMessage.getReserved()) {
            bookingStatsRepository.save(BookingStatisticsMessage.bookingStatisticsFromMessage(bookingStatisticsMessage));
        } else {
            Iterable<BookingStatistics> bookingStatistics = bookingStatsRepository.findAllByHotelUidAndUserUidAndDate(
                    bookingStatisticsMessage.getHotelUid(), bookingStatisticsMessage.getUserUid(), bookingStatisticsMessage.getDate());
            if (bookingStatistics.iterator().hasNext()) {
                bookingStatsRepository.delete(bookingStatistics.iterator().next());
            }
        }
    }

    @Override
    public Iterable<BookingStatisticsMessage> getUserStatistics(String from, String to) {
        ArrayList<BookingStatisticsMessage> bookingStatisticsMessages = new ArrayList<>();
        Iterable<BookingStatistics> bookingStatistics = bookingStatsRepository.findByTimestampBetween(from, to);
        for (BookingStatistics bs: bookingStatistics) {
                bookingStatisticsMessages.add(BookingStatisticsMessage.bookingStatisticsMessageToMessage(bs));
            }
        return bookingStatisticsMessages;
    }

    @Override
    public Iterable<HotelFillingStatistics> getHotelStatistics(String from, String to) {
        ArrayList<HotelFillingStatistics> hotelFillingStatisticsArray = new ArrayList<>();
        HashMap<String, HashMap<String, Integer>> filling = new HashMap<>();
        Iterable<BookingStatistics> bookingStatistics = bookingStatsRepository.findByDateBetween(from, to);
        for (BookingStatistics bs: bookingStatistics) {
            if (!filling.containsKey(bs.getHotelUid().toString())) {
                filling.put(bs.getHotelUid().toString(), new HashMap<>());
                filling.get(bs.getHotelUid().toString()).put(bs.getDate(), 1);
            } else if (!filling.get(bs.getHotelUid().toString()).containsKey(bs.getDate())) {
                filling.get(bs.getHotelUid().toString()).put(bs.getDate(), 1);
            } else {
                filling.get(bs.getHotelUid().toString()).put(bs.getDate(),
                        filling.get(bs.getHotelUid().toString()).get(bs.getDate()) + 1);
            }
        }
        for (String hotelUid:filling.keySet()) {
            for (String date:filling.get(hotelUid).keySet()) {
                HotelFillingStatistics hotelFillingStatistics = new HotelFillingStatistics();
                hotelFillingStatistics.setRoomsFilling(filling.get(hotelUid).get(date));
                hotelFillingStatistics.setDate(date);
                hotelFillingStatistics.setHotelUid(UUID.fromString(hotelUid));
                hotelFillingStatisticsArray.add(hotelFillingStatistics);
            }
        }
        return hotelFillingStatisticsArray;
    }
}
