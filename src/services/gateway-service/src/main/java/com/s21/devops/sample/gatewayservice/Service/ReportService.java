package com.s21.devops.sample.gatewayservice.Service;

import com.s21.devops.sample.gatewayservice.Communication.BookingStatisticsMessage;
import com.s21.devops.sample.gatewayservice.Communication.HotelFillingStatistics;
import com.s21.devops.sample.gatewayservice.Exception.CustomJwtException;
import com.s21.devops.sample.gatewayservice.Exception.CustomRuntimeException;
import org.springframework.stereotype.Service;


@Service
public interface ReportService {
    public void auth()
            throws CustomJwtException, CustomRuntimeException;
    public BookingStatisticsMessage[] getUserStatistics(String from, String to)
            throws CustomJwtException, CustomRuntimeException;
    public HotelFillingStatistics[] getHotelStatistics(String from, String to)
            throws CustomJwtException, CustomRuntimeException;
}
