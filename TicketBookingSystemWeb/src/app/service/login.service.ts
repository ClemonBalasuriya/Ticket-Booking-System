import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Customer } from '../model/customer';
import { Observable } from 'rxjs';
import { Vendor } from '../model/vendor';

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

  private vendorInfo: any;
  setvendorInfo(vendor: any) {
    this.vendorInfo = vendor;
  }

  getvendorInfo() {
    return this.vendorInfo;
  }


  public loginCustomerFromRemort(customer: Customer):Observable<any>{
    return this._http.post<any>("http://localhost:8080/customerlogin", customer);
  }
  public loginVendorFromRemort(vendor: Vendor):Observable<any>{
    return this._http.post<any>("http://localhost:8080/vendorlogin", vendor);
  }
  registerCustomer(customer: Customer): Observable<Customer> {
    return this._http.post<Customer>("http://localhost:8080/registercustomer", customer);
  }
  registerVendor(vendor: Vendor): Observable<Customer> {
    return this._http.post<Vendor>("http://localhost:8080/registervendor", vendor);
  }
}
