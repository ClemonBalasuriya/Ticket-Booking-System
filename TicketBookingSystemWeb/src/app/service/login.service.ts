import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Customer } from '../model/customer';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  constructor(private _http:HttpClient) { }

  private customerInfo: any;
  setCustomerInfo(customer: any) {
    this.customerInfo = customer;
  }

  getCustomerInfo() {
    return this.customerInfo;
  }

  public loginCustomerFromRemort(customer: Customer):Observable<any>{
    return this._http.post<any>("http://localhost:8080/customerlogin", customer);
  }
  public loginVendorFromRemort(customer: Customer):Observable<any>{
    return this._http.post<any>("http://localhost:8080/vendorlogin", customer);
  }
  registerCustomer(customer: Customer): Observable<Customer> {
    return this._http.post<Customer>("http://localhost:8080/registercustomer", customer);
  }
  registerVendor(customer: Customer): Observable<Customer> {
    return this._http.post<Customer>("http://localhost:8080/registervendor", customer);
  }
}
