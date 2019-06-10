package com.hankyz.ymcabooking.controller;

import com.hankyz.ymcabooking.handler.WebDriverHandler;


import java.time.LocalDateTime;
import java.time.ZoneId;

public class YmcaBookingController {

    private final WebDriverHandler webDriverHandler;

    public YmcaBookingController() {
        webDriverHandler = WebDriverHandler.getInstance();
    }



    public void doBooking() {

        webDriverHandler.book();
    }
}
