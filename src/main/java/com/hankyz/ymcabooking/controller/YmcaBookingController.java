package com.hankyz.ymcabooking.controller;

import com.hankyz.ymcabooking.handler.WebDriverHandler;


import java.time.LocalDateTime;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;

public class YmcaBookingController {

    private final WebDriverHandler webDriverHandler;

    public YmcaBookingController() {
        webDriverHandler = WebDriverHandler.getInstance();
    }

    //method increment two days
    private LocalDateTime getDateTime() {
        //get midnight
        LocalTime midnight = LocalTime.MIDNIGHT;
        //get time zone
        LocalDate today = LocalDate.now(ZoneId.of("America/Montreal"));
        //return two days later
        return LocalDateTime.of(today, midnight).plusDays(2);
    }

    public void doBooking() {
        // get two days later
        LocalDateTime twoDays = getDateTime();
        String day = String.valueOf(twoDays.getDayOfMonth());
        String month = String.valueOf(twoDays.getMonthValue());
        String year = String.valueOf(twoDays.getYear());

        webDriverHandler.book(day, month, year);
    }
}
