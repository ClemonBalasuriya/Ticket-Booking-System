import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Config } from '../model/config';

@Injectable({
  providedIn: 'root'
})
export class ConfigService {

  constructor(private _http:HttpClient) { }

  public getTicketsSummary():Observable<any>{
    return this._http.get<any>("http://localhost:8080");
  }
  public changeConfig(config: Config):Observable<any>{
    return this._http.post<any>("http://localhost:8080/changeconfig", config);
  }

  
  
  

  
}
