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
            // Retrieve the maximum number of tickets the vendor can issue
            int numOfTicketCan = issuingService.getCountOfTicketCan(threadName);


            if (numOfTickets < issuingService.onlyCanAddTicket()) {
                if (numOfTicketCan >= numOfTickets){
                    // Proceed to book tickets
                    for (int i = 0; i < numOfTickets; i++) {
                        issuingService.issueForTicketPool(vendor);
                    }
                    issuingService.editNumOfReleasedTicketAndAvailableTickets();

                }
            }else{
                System.out.println("Try to add existing ticket than the number of can add");
            }


        } catch (Exception e) {
            // Log the exception and notify Angular
            System.err.println("Error in ticket booking: " + e.getMessage());
        }

    }
}
