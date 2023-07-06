import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Flight } from '../flight';
import { Observable, map } from 'rxjs';
import { Result } from 'src/result';


@Injectable({
  providedIn: 'root'
})
export class FlightServiceService {

  private flightUrl: string;

  constructor(private http: HttpClient) {

    this.flightUrl = 'http://127.0.0.1:8080/api/v1/ticketmaster/get'

  }

  public get(flight: Flight): Observable<Result> {
    const params = new HttpParams()
      .set('arrival', flight.to)
      .set('departure', flight.from)
      .set('date', flight.date.toISOString());

    return this.http.get<Result>(this.flightUrl, { params: params });
  }
}
