package com.s21.devops.sample.reportservice.Controller;

import com.s21.devops.sample.reportservice.Communication.BookingStatisticsMessage;
import com.s21.devops.sample.reportservice.Communication.HotelFillingStatistics;
import com.s21.devops.sample.reportservice.Service.BookingStatsService;
import com.s21.devops.sample.reportservice.Service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@RestController
@RequestMapping("/api/v1/report")
public class ReportController {
    @Autowired
    private BookingStatsService bookingStatsService;

    @Autowired
    private SecurityService securityService;

    @GetMapping("/users")
    public Iterable<BookingStatisticsMessage> getBookingStats(@RequestParam("from") String from, @RequestParam("to") String to) {
        return bookingStatsService.getUserStatistics(from, to);
    }

    @GetMapping("/hotels")
    public Iterable<HotelFillingStatistics> getHotelFillingStats(@RequestParam("from") String from, @RequestParam("to") String to) {
        return bookingStatsService.getHotelStatistics(from, to);
    }

    @GetMapping("/authorize")
    public ResponseEntity<Void> authorize(@RequestHeader("authorization") String authorization)
            throws InvalidKeySpecException, NoSuchAlgorithmException, EntityNotFoundException {
        String jwtToken = securityService.authorize(authorization);
        return ResponseEntity.ok().header("Authorization", "Bearer " + jwtToken).build();
    }

}
