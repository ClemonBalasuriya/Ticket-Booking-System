package com.example.Ticket.Booking.System;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Hello {
    @RequestMapping("/")
    public String login(){
        return "Customer Login Success";
    }
}
