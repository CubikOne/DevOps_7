package com.s21.devops.sample.bookingservice.Repository;

import com.s21.devops.sample.bookingservice.Model.Reservation;
import com.s21.devops.sample.bookingservice.Model.ReservationCount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    public void deleteAllByHotelUidAndUserUid(UUID hotelUid, UUID userUid);
    public Iterable<Reservation> findAllByHotelUidAndUserUid(UUID hotelUid, UUID userUid);
    public Iterable<Reservation> findAllByUserUid(UUID userUid);
    public Iterable<Reservation> findAllByHotelUid(UUID hotelUid);
}
