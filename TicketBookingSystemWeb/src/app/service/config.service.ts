import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { Config } from '../model/config';

@Injectable({
  providedIn: 'root'
})
export class ConfigService {

  constructor(private _http:HttpClient) { }

  private ticketsAvailableSource = new BehaviorSubject<number>(0); // Default to 0
  ticketsAvailable$ = this.ticketsAvailableSource.asObservable();

  private retrivalRateSource = new BehaviorSubject<number>(0); // Default to 0
  retrivalRate$ = this.retrivalRateSource.asObservable();

  

  // Update ticketsAvailable
  updateTicketsAvailable(value: number) {
    this.ticketsAvailableSource.next(value);
  }

  // Update retrivalRate
  updateRetrivalRate(value: number) {
    this.retrivalRateSource.next(value);
  }

  public getTicketsSummary():Observable<any>{
    return this._http.get<any>("http://localhost:8080");
    
  }
  public changeConfig(config: Config):Observable<any>{
    return this._http.post<any>("http://localhost:8080/changeconfig", config);
  }

  
  
  

  
}
