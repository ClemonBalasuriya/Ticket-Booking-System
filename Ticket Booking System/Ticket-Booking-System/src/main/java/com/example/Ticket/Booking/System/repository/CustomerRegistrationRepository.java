package com.example.Ticket.Booking.System.repository;

import com.example.Ticket.Booking.System.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRegistrationRepository extends JpaRepository<Customer, Integer> {

    public Customer findByEmail(String email);
    public Customer findByEmailAndPassword(String email, String password);

}
