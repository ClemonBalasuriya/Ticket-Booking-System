import { Component } from '@angular/core';
import { LoginService } from '../../service/login.service';
import { Customer } from '../../model/customer';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { NgIf } from '@angular/common';
import { Vendor } from '../../model/vendor';

@Component({
  selector: 'app-login',
  imports: [FormsModule,NgIf],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
  customer = new Customer();
  vendor = new Vendor();

  currentModel: Customer | Vendor = this.customer;
  msg = '';
  selectedRole: 'Customer' | 'Staff' = 'Customer'; // Default to Customer

  

  constructor(private _service: LoginService, private _router: Router) {}



  // Login method
  login() {
    if (this.selectedRole === 'Customer') {
      this._service.loginCustomerFromRemort(this.customer).subscribe(
        response => {
          console.log('Customer logged in successfully:');
          // Store customer info in the service
          this._service.setCustomerInfo(this.customer);
          this._router.navigate(['ticketbooking']);
        },
        error => {
          this.msg = 'Enter the valid email id and password.';
          console.log('An error occurred:', error);
        }
      );
    } else if (this.selectedRole === 'Staff') {
 

      this._service.loginVendorFromRemort(this.vendor).subscribe(
        response => {
          console.log('Vendor logged in successfully:');
          // Store customer info in the service
          this._service.setvendorInfo(this.vendor);
          
          this._router.navigate(['ticketrelease']);
        },
        error => {
          this.msg = 'Enter the valid email id and password.';
          console.log('An error occurred:', error);
        }
      );
    }
  }

  // Toggle role (Customer/Staff)
  selectRole(role: 'Customer' | 'Staff') {
    this.selectedRole = role;
    console.log(`${role} selected`);
    this.currentModel = (role === 'Customer') ? this.customer : this.vendor;
  }

  // Navigate to registration
  goToRegistration() {
    this._router.navigate(['registration']);
  }
  goToconfigerPage(){
    this._router.navigate(['']);
  }

}
