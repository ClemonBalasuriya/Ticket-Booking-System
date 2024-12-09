import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { BookingRequest } from '../model/booking-request';

@Injectable({
  providedIn: 'root'
})
export class BookingService {


  

  constructor(private http: HttpClient) {}


  purchaseTickets(data: BookingRequest): Observable<any> {
    return this.http.post<any>('http://localhost:8080/ticketbooking', data);
  }


}
