import { Component } from '@angular/core';
import { Customer } from '../../model/customer';
import { Vendor } from '../../model/vendor';
import { LoginService } from '../../service/login.service';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { NgIf } from '@angular/common';

@Component({
  selector: 'app-registration',
  imports: [FormsModule,NgIf],
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css', '../login/login.component.css']

})
export class RegistrationComponent {
  constructor(private _service: LoginService, private _router: Router) {}
  customer = new Customer();
  vendor = new Vendor();
  registrationError='';

  selectedRole: string = 'Customer'; // Default role
  user = {
    username: '',
    email: '',
    phone: '',
    password: ''
  };

 

  register() {

   // If the selected role is 'Customer', convert user to a Customer object
   if(this.selectedRole == 'Customer') {
    this.customer.username = this.user.username;
    this.customer.email = this.user.email;
    this.customer.phone = this.user.phone;
    this.customer.password = this.user.password;

    // Call the service to send data to the Spring Boot backend
    this._service.registerCustomer(this.customer).subscribe(
      (response) => {
        console.log('Customer registered:', response);
        this._router.navigate(['login']);  // Navigate to the login page on success
      },
      (error) => {
        console.error('Registration failed:', error);
        this.registrationError = "This email is already exists.";
      }
    );
  }
  // Else for Staff, implement the logic (if needed)
  else {
    this.vendor.username = this.user.username;
    this.vendor.email = this.user.email;
    this.vendor.phone = this.user.phone;
    this.vendor.password = this.user.password;

    this._service.registerVendor(this.vendor).subscribe(
      (response) => {
        console.log('Staff registered:', response);
        this._router.navigate(['login']);  // Navigate to the login page on success
      },
      (error) => {
        console.error('Registration failed:', error);
        this.registrationError = "This email is already exists.";
      }
    );
  }
    
  }

  

  // Toggle role (Customer/Staff)
  selectRole(role: 'Customer' | 'Staff') {
    this.selectedRole = role;
    console.log(`${role} selected`);
  }

  // Navigate to login
  goToLogin(){
    this._router.navigate(['login']);
  }

  //clear registrationError
  clearErrorMessage() {
    this.registrationError = '';
  }

}
