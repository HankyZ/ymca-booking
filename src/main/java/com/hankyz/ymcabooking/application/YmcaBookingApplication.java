package com.hankyz.ymcabooking.application;

import com.hankyz.ymcabooking.controller.YmcaBookingController;

public class YmcaBookingApplication {

    public static void main(String[] args) {
        YmcaBookingController ymcaBookingController = new YmcaBookingController();
        ymcaBookingController.doBooking();
    }
}
