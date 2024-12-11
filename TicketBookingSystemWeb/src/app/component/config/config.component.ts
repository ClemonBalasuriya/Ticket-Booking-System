import { NgIf } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { ConfigService } from '../../service/config.service';
import { error } from 'console';
import { config } from 'process';
import { Config } from '../../model/config';
import { response } from 'express';
import { Router, RouterLink } from '@angular/router';

@Component({
  selector: 'app-config',
  imports: [FormsModule,NgIf],
  templateUrl: './config.component.html',
  styleUrls: ['../login/login.component.css','./config.component.css']
})
export class ConfigComponent implements OnInit {

  config = new Config();

  totTickets: number = 0;
  ticketsReleased: number = 0;
  ticketsAvailable: number = 0;
  customer_retrieval_rate: number = 0;
  vendor_release_rate: number = 0;

  constructor(private configServise: ConfigService,private _router:Router) {}

  ngOnInit(): void {
    this.loadTicketSummary();
    


  }

  loadTicketSummary(): void {
    this.configServise.getTicketsSummary().subscribe(
      (data) =>{
        this.totTickets = data.totalTickets;
        this.ticketsReleased = data.ticketsReleased;
        this.ticketsAvailable = data.ticketsAvailable;
        this.customer_retrieval_rate = data.customer_retrieval_rate;
        this.vendor_release_rate = data.vendor_release_rate;

        this.config.totalTickets=this.totTickets;
        this.config.ticketsReleased=this.ticketsReleased;
        this.config.ticketsAvailable=this.ticketsAvailable;
        this.config.customer_retrieval_rate=this.customer_retrieval_rate;
        this.config.vendor_release_rate=this.vendor_release_rate;


      },
      (error ) => console.error("Error in Ticket Summary",error)
    );
  }


  saveConfig(): void {
    if(this.ticketsReleased>this.config.totalTickets){
      alert("Total Ticket should be more than Release Count!")
      this.config.totalTickets = this.totTickets;
      this.config.customer_retrieval_rate = this.customer_retrieval_rate;
      this.config.vendor_release_rate = this.vendor_release_rate;
      return;
    }
    if (this.validateInputs()) {
      this.totTickets = this.config.totalTickets;
      this.customer_retrieval_rate = this.config.customer_retrieval_rate;
      this.vendor_release_rate = this.config.vendor_release_rate;

      this.configServise.changeConfig(this.config).subscribe(
        response =>{
          console.log('Config change');
        },
        error=>{
          console.log('Config change error occured: ',error);
        }
      )
      
    }
  
    
  }
  

 


 

  // Message for validation feedback
  validationMessage: string = '';

  // Start and stop state
  systemRunning: boolean = false;

  // Handle the "Start" button click
  startSystem(): void {
    if (this.validateInputs()) {
      this.systemRunning = true;
      this.validationMessage = '';
      console.log('System started with the following configuration:');
      console.log({
        totalTickets: this.totTickets,
        ticketsReleased: this.ticketsReleased,
        ticketReleaseRate: this.vendor_release_rate,
        customerRetrievalRate: this.customer_retrieval_rate,
        maxTicketCapacity: this.totTickets,
      });
      this._router.navigate(['login']);
    }
  }

  // Handle the "Stop" button click
  stopSystem(): void {
    this.systemRunning = false;
    console.log('System stopped.');
  }

  // Validate inputs
  private validateInputs(): boolean {
    if (
      this.totTickets <= 0 ||
      this.vendor_release_rate <= 0 ||
      this.customer_retrieval_rate <= 0 ||
      this.totTickets <= 0 ||
      this.totTickets > this.totTickets
    ) {
      this.validationMessage =
        'Please ensure all inputs are positive numbers, and total tickets do not exceed the maximum ticket capacity.';
      return false;
    }
    return true;
  }
}
