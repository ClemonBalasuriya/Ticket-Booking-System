import { Component, OnInit } from '@angular/core';
import { ConfigComponent } from '../config/config.component';
import { FormsModule } from '@angular/forms';
import { ConfigService } from '../../service/config.service';
import { BookingService } from '../../service/booking.service';
import { NgIf } from '@angular/common';
import { LoginService } from '../../service/login.service';
import { BookingRequest } from '../../model/booking-request';

@Component({
  selector: 'app-ticketbooking',
  imports: [FormsModule],
  templateUrl: './ticketbooking.component.html',
  styleUrls: ['../login/login.component.css','../config/config.component.css','./ticketbooking.component.css']
})
export class TicketbookingComponent implements OnInit {

  customerInfo: any;
  ticketsAvailable: number = 0;
  retrivalRate: number = 0;
  ticketCount: number | null = null; // Stores the input ticket count

  constructor(private configServise: ConfigService, private bookingService: BookingService, private loginService:LoginService){}

  // This function will be called when backend sends a notification
  handleTicketNotification(message: string) {
    alert(message); // Example: Show an alert with the notification message
  }
  
  

  ngOnInit(): void {
    this.loadTicketSummary();
    // Get the customer info from the service
    this.customerInfo = this.loginService.getCustomerInfo();
  }

  loadTicketSummary(): void {
    this.configServise.getTicketsSummary().subscribe(
      (data) =>{
        this.ticketsAvailable =  data.ticketsAvailable;
        this.retrivalRate = data.customer_retrieval_rate;

      },
      (error ) => console.error("Error in Ticket Summary",error)
    );

  }

  // Validate the ticket count entered
  validateTicketCount(): void {
    
    if (this.ticketCount !== null) {
      
      if (this.ticketCount < 1) {
        alert('Ticket count cannot be less than 1.');
        
      } else if (this.ticketCount > this.ticketsAvailable) {
          if(this.retrivalRate > this.ticketsAvailable){
            alert(`Ticket count cannot be more than available tickets (${this.ticketsAvailable}).`);
          }else{
            alert(`Ticket count cannot be more than available tickets (${this.retrivalRate}).`);
          }
        
      }else if(this.ticketCount > this.retrivalRate){
        alert(`Ticket count cannot be more than available (${this.retrivalRate}) tickets .`);

      }
      this.ticketCount = 0; // Reset to 0 if invalid value
    }
  }

  purchaseTickets(): void {
    

    if (this.ticketCount && this.ticketCount > 0 && this.ticketCount <= this.ticketsAvailable) {
      let data = new BookingRequest();
      data.setCustomer(this.customerInfo);
      data.setnumOfTickets(this.ticketCount);
      
      // Call the booking service to process the ticket purchase
      this.bookingService.purchaseTickets(data).subscribe(
        (response: number) => {  // response is the string from the backend
          this.ticketsAvailable = response;
          this.loadTicketSummary(); // Refresh the available tickets after purchase
        },
        (error) => {
          console.error('Error purchasing tickets', error);
          alert('Error purchasing tickets. Please try again later.');
        }
      );
    }
    this.loadTicketSummary();
  }



}
