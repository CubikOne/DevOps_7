package com.s21.devops.sample.reportservice.Repository;

import com.s21.devops.sample.reportservice.Model.BookingStatistics;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface BookingStatsRepository extends JpaRepository<BookingStatistics, Long> {
    public Optional<BookingStatistics> findAllByHotelUid(UUID hotelUid);
    public Iterable<BookingStatistics> findByTimestampBetween(String from, String to);
    public Iterable<BookingStatistics> findByDateBetween(String from, String to);
    public Iterable<BookingStatistics> findAllByHotelUidAndUserUidAndDate(UUID hotelUid, UUID userUid, String date);
}
