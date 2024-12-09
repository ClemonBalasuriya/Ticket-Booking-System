package com.example.Ticket.Booking.System.controller;

import com.example.Ticket.Booking.System.model.Customer;
import com.example.Ticket.Booking.System.service.CustomerRegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class CustomerRegistrationController {

    @Autowired
    private CustomerRegistrationService customerRegistrationService;

    @PostMapping("/registercustomer")
    @CrossOrigin(origins = "http://localhost:4200")
    public Customer registerCustomer(@RequestBody Customer customer) throws Exception {
        String tempEmail = customer.getEmail();
        if (tempEmail != null && !"".equals(tempEmail)  ) {
            Optional<Customer> customerOb = customerRegistrationService.fetchCustomerByEmail(tempEmail);
            Customer customerObj = customerOb.get();
            if (customerObj != null) {
                throw new Exception("Customer with"+tempEmail+"is already exist.");
            }
        }
        Customer customerObj = null;
        customerObj = customerRegistrationService.saveCustomer(customer);
        return customerObj;
    }

    @PostMapping("/customerlogin")
    @CrossOrigin(origins = "http://localhost:4200")
    public Customer loginCustomer(@RequestBody Customer customer) throws Exception {

        String tempEmail = customer.getEmail();
        String tempPassword = customer.getPassword();
        Customer customerObj =null;
        if (tempEmail != null && tempPassword != null  ) {
            customerObj = customerRegistrationService.fetchCustomerByEmailAndPassword(tempEmail, tempPassword);

        }
        if (customerObj == null) {
            throw new Exception("Bad credentials");
        }

        return customerObj;
    }
}
