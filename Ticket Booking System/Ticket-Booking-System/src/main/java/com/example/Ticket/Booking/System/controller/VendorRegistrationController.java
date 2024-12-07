package com.example.Ticket.Booking.System.controller;

import com.example.Ticket.Booking.System.model.Vendor;
import com.example.Ticket.Booking.System.service.VendorRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VendorRegistrationController {

    @Autowired
    private VendorRegistrationService vendorRegistrationService;

    @PostMapping("/registervendor")
    @CrossOrigin(origins = "http://localhost:4200")
    public Vendor registerVendor(@RequestBody Vendor vendor) throws Exception {
        String tempEmail = vendor.getEmail();
        if (tempEmail != null && !"".equals(tempEmail)) {
            Vendor vendorObj = vendorRegistrationService.fetchVendorByEmail(tempEmail);
            if (vendorObj != null) {
                throw new Exception("Vendor with " + tempEmail + " already exists.");
            }
        }
        Vendor vendorObj = null;
        vendorObj = vendorRegistrationService.saveVendor(vendor);
        return vendorObj;
    }

    @PostMapping("/vendorlogin")
    @CrossOrigin(origins = "http://localhost:4200")
    public Vendor loginVendor(@RequestBody Vendor vendor) throws Exception {

        String tempEmail = vendor.getEmail();
        String tempPassword = vendor.getPassword();
        Vendor vendorObj = null;
        if (tempEmail != null && tempPassword != null) {
            vendorObj = vendorRegistrationService.fetchVendorByEmailAndPassword(tempEmail, tempPassword);
        }
        if (vendorObj == null) {
            throw new Exception("Bad credentials");
        }

        return vendorObj;
    }

}
