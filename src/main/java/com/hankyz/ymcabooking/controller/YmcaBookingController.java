package com.hankyz.ymcabooking.controller;

import com.hankyz.ymcabooking.handler.WebDriverHandler;

public class YmcaBookingController {

    private final WebDriverHandler webDriverHandler;

    public YmcaBookingController() {
        webDriverHandler = WebDriverHandler.getInstance();
    }

    public void doBooking() {
        String day = "3";
        String month = "6";
        String year = "2019";

        webDriverHandler.book(day, month, year);
    }
}
