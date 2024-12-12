package com.example.Ticket.Booking.System.model;

import com.example.Ticket.Booking.System.service.BookingService;
import com.example.Ticket.Booking.System.service.IssuingService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TicketReleasingRequest implements Runnable {
    private Vendor vendor;
    private int numOfTickets;
    private IssuingService issuingService;

    public void run() {

        String threadName = Thread.currentThread().getName();
        try {
            int numOfTicketCan = issuingService.getCountOfTicketCan(threadName,numOfTickets,vendor,issuingService);

            for (int i = 0; i < numOfTicketCan; i++) {
                issuingService.releasingTickets(vendor);
            }

            int updatedTicketsReleased = issuingService.setTicketPollConfig(numOfTicketCan);
            System.out.println(updatedTicketsReleased + " tickets released totally.");


            //issuingService.setVal(numOfTicketCan);

        } catch (Exception e) {
            System.err.println("Error in ticket issuing: " + e.getMessage());
        }

    }
}
