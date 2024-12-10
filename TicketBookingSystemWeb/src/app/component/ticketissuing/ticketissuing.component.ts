import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ConfigService } from '../../service/config.service';
import { RelesingService } from '../../service/relesing.service';
import { LoginService } from '../../service/login.service';
import { IssuingRequest } from '../../model/issuing-request';

@Component({
  selector: 'app-ticketissuing',
  imports: [FormsModule],
  templateUrl: './ticketissuing.component.html',
  styleUrls: ['../login/login.component.css','../config/config.component.css','../ticketbooking/ticketbooking.component.css','./ticketissuing.component.css']
  })
export class TicketissuingComponent implements OnInit{

  vendorInfo: any;
  availableaddCount: number = 0;
  releaseRate: number = 0;
  ticketCount: number | null = null; // Stores the input ticket count

  constructor(private configServise: ConfigService, private releasingService: RelesingService, private loginService:LoginService){}

  ngOnInit(): void {
    this.loadTicketSummary();
    // Get the customer info from the service
    this.vendorInfo = this.loginService.getvendorInfo();
  }

  loadTicketSummary(): void {
    this.configServise.getTicketsSummary().subscribe(
      (data) =>{
        this.availableaddCount =  (data.totalTickets-data.ticketsReleased);
        this.releaseRate = data.vendor_release_rate;

      },
      (error ) => console.error("Error in Ticket Summary",error)
    );

  }

  // Validate the ticket count entered
  validateTicketCount(): void {
    
    if (this.ticketCount !== null) {
      
      if (this.ticketCount < 1) {
        this.ticketCount = 0; // Reset to 0 if invalid value
        alert('Ticket count cannot be less than 1.');
        
      } else if (this.ticketCount >this.availableaddCount) {
          if(this.releaseRate > this.availableaddCount){
            this.ticketCount =  this.availableaddCount; 
            alert(`Ticket count cannot be more than available tickets (${this.availableaddCount}).`);
          }else{
            this.ticketCount =  this.releaseRate;
            alert(`Ticket count cannot be more than available tickets (${this.releaseRate}).`);
          }
        
      }else if(this.ticketCount > this.releaseRate){
        this.ticketCount =  this.releaseRate;
        alert(`Ticket count cannot be more than available (${this.releaseRate}) tickets .`);

      }
      
    }
  }

  issuingTickets(): void {
    
    if (this.ticketCount && this.ticketCount > 0 && this.ticketCount <= this.availableaddCount) {
      let data = new IssuingRequest();
      data.setVendor(this.vendorInfo);
      data.setnumOfTickets(this.ticketCount);
      
      this.releasingService.issuingTickets(data).subscribe(
        (response: number) => {  // response is the string from the backend
          this.availableaddCount = response;
          this.ticketCount =0;
          this.loadTicketSummary(); // Refresh the available tickets after purchase
          alert("Ticket Issued.")
        },
        (error) => {
          console.error('Error issuing tickets', error);
          alert('Error issuing tickets. Please try again later.');
        }
      );
    }
    this.loadTicketSummary();
  }


}
