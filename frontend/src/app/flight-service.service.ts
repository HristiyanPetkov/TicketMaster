import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Flight } from '../flight';
import { Observable, map } from 'rxjs';
import { Result } from 'src/result';
import { environment } from 'src/environment/environment';


@Injectable({
  providedIn: 'root'
})
export class FlightServiceService {
  private flightUrl: string;

  constructor(private http: HttpClient) {
    this.flightUrl = environment.flightUrl
  }

  public get(flight: Flight): Observable<Result[]> {
    const params = new HttpParams()
      .set('arrivalAirport', flight.to)
      .set('departureAirport', flight.from)
      .set('arrival_date', new Date(flight.arrivalDate).toISOString())
      .set('departure_date', new Date(flight.departureDate).toISOString());

    return this.http.get<Result[]>(`${this.flightUrl}/get`, { params: params });
  }

  public getSuggestions(query: string): Observable<string[]> {
    const params = new HttpParams()
      .set('query', query);

    return this.http.get<string[]>(`${this.flightUrl}/search`, { params: params });
  }
}
