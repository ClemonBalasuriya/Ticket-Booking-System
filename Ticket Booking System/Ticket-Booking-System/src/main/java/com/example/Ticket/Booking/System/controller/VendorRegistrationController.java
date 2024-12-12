package com.example.Ticket.Booking.System.controller;

import com.example.Ticket.Booking.System.model.Customer;
import com.example.Ticket.Booking.System.model.Vendor;
import com.example.Ticket.Booking.System.service.VendorRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
public class VendorRegistrationController {

    @Autowired
    private VendorRegistrationService vendorRegistrationService;

    @PostMapping("/registervendor")
    @CrossOrigin(origins = "http://localhost:4200")
    public Vendor registerVendor(@RequestBody Vendor vendor) throws Exception {
        String tempEmail = vendor.getEmail();

        if (tempEmail != null && !"".equals(tempEmail)) {
            Optional<Vendor> vendorOb = vendorRegistrationService.fetchVendorByEmail(tempEmail);
            if (vendorOb.isPresent()) {
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
        System.out.println(tempEmail);
        System.out.println(tempPassword);


        Vendor vendorObj = null;
        if (tempEmail != null && tempPassword != null) {
            vendorObj = vendorRegistrationService.fetchVendorByEmailAndPassword(tempEmail, tempPassword);
        }
        if (vendorObj == null) {
            throw new Exception("Bad credentials");
        }
        System.out.println(vendorObj.getEmail());
        System.out.println(vendorObj.getPassword());

        return vendorObj;
    }

}
