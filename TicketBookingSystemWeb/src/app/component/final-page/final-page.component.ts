import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { ConfigService } from '../../service/config.service';
import { Config } from '../../model/config';

@Component({
  selector: 'app-final-page',
  imports: [],
  templateUrl: './final-page.component.html',
  styleUrls: ['../login/login.component.css','../config/config.component.css','./final-page.component.css']
  
})
export class FinalPageComponent {
  // Final configuration values
  finalConfig: Config = new Config();
  totTickets: number = 0;
  ticketsReleased: number = 0;
  ticketsAvailable: number = 0;
  customer_retrieval_rate: number = 0;
  vendor_release_rate: number = 0;

  constructor(private configService: ConfigService, private router: Router) {}

  ngOnInit(): void {
    this.loadFinalValues();
  }

  // Fetch the final values when the system stops
  loadFinalValues(): void {
    this.configService.getTicketsSummary().subscribe(
      (data) => {
        this.totTickets = data.totalTickets;
        this.ticketsReleased = data.ticketsReleased;
        this.ticketsAvailable = data.ticketsAvailable;
        this.customer_retrieval_rate = data.customer_retrieval_rate;
        this.vendor_release_rate = data.vendor_release_rate;

        // Set the final configuration object
        this.finalConfig.totalTickets = this.totTickets;
        this.finalConfig.ticketsReleased = this.ticketsReleased;
        this.finalConfig.ticketsAvailable = this.ticketsAvailable;
        this.finalConfig.customer_retrieval_rate = this.customer_retrieval_rate;
        this.finalConfig.vendor_release_rate = this.vendor_release_rate;

        console.log('Final values loaded:', this.finalConfig);
      },
      (error) => {
        console.error('Error fetching final values:', error);
      }
    );
  }

  // Navigate back to the home page
  navigateToHome(): void {
    this.router.navigate(['/']); // Adjust the path if your home route is different
  }

  
}
