package com.example.Ticket.Booking.System.service;

import com.example.Ticket.Booking.System.model.Customer;
import com.example.Ticket.Booking.System.repository.CustomerRegistrationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerRegistrationService {

    @Autowired
    private CustomerRegistrationRepository customerRegistrationRepository;

    public Customer saveCustomer(Customer customer) {
        return customerRegistrationRepository.save(customer);
    }

    public Optional<Customer> fetchCustomerByEmail(String email) {
        return customerRegistrationRepository.findByEmail(email);
    }
    public Customer fetchCustomerByEmailAndPassword(String email, String password) {
        return customerRegistrationRepository.findByEmailAndPassword(email, password);
    }
}
