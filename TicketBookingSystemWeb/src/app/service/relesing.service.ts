import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { IssuingRequest } from '../model/issuing-request';

@Injectable({
  providedIn: 'root'
})
export class RelesingService {
  constructor(private http: HttpClient) {}


  issuingTickets(data: IssuingRequest): Observable<any> {
    return this.http.post<any>('http://localhost:8080/ticketreleasing', data);
  }

  getVal(): Observable<number> {
    return this.http.get<number>(`http://localhost:8080/ticketreleasingdata`); 
  }


}
