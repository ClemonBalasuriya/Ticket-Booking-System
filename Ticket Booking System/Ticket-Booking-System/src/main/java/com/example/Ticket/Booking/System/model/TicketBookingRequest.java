package com.example.Ticket.Booking.System.model;

import com.example.Ticket.Booking.System.service.BookingService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class TicketBookingRequest implements Runnable {
    private Customer customer;
    private int numOfTickets;
    private BookingService bookingService;


    public void run() {
        String threadName = Thread.currentThread().getName();
        try {
            // Retrieve the maximum number of tickets the customer can buy
            int numOfTicketCan = bookingService.getCountOfTicketCan(threadName,numOfTickets,customer,bookingService);

            // Proceed to book tickets
            for (int i = 0; i < numOfTicketCan; i++) {
                bookingService.bookingFromTicketPool(customer);
            }
            int updatedTicketsPurchase =bookingService.setTicketPollConfig(numOfTicketCan);

            System.out.println(updatedTicketsPurchase + " tickets released.");

            bookingService.setVal(updatedTicketsPurchase); // Set latest count

        } catch (Exception e) {
            // Log the exception and notify Angular
            System.err.println("Error in ticket booking: " + e.getMessage());
        }

    }
}
