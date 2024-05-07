package com.s21.devops.sample.reportservice.Service;

import com.s21.devops.sample.reportservice.Communication.BookingStatisticsMessage;
import com.s21.devops.sample.reportservice.Communication.HotelFillingStatistics;
import org.springframework.stereotype.Service;

@Service
public interface BookingStatsService {
    public void postBookingStatsMessage(BookingStatisticsMessage bookingStatisticsMessage);
    public Iterable<BookingStatisticsMessage> getUserStatistics(String from, String to);
    public Iterable<HotelFillingStatistics> getHotelStatistics(String from, String to);
}
