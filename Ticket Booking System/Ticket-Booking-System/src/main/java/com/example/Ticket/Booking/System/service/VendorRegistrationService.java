package com.example.Ticket.Booking.System.service;

import com.example.Ticket.Booking.System.model.Customer;
import com.example.Ticket.Booking.System.model.Vendor;
import com.example.Ticket.Booking.System.repository.CustomerRegistrationRepository;
import com.example.Ticket.Booking.System.repository.VendorRegistrationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VendorRegistrationService {

    @Autowired
    private VendorRegistrationRepository vendorRegistrationRepository;

    public Vendor saveVendor(Vendor vendor) {
        return vendorRegistrationRepository.save(vendor);
    }

    public Vendor fetchVendorByEmail(String email) {
        return vendorRegistrationRepository.findByEmail(email);
    }
    public Vendor fetchVendorByEmailAndPassword(String email, String password) {
        return vendorRegistrationRepository.findByEmailAndPassword(email, password);
    }
}
