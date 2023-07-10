import { Component } from '@angular/core';
import { Flight } from '../../flight';
import { FlightServiceService } from '../flight-service.service';
import { Result } from 'src/result';
import { Router } from '@angular/router';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable, map } from 'rxjs';
import { Airport } from 'src/airport';

@Component({
  selector: 'app-flight-form',
  templateUrl: './flight-form.component.html',
  styleUrls: ['./flight-form.component.css']
})
export class FlightFormComponent {
  minDate: Date;
  flight: Flight;
  public showArrival: boolean = true;
  public showDeparture: boolean = true;
  fromTerm: string = '';
  fromResults: Airport[] = [];

  toTerm: string = '';
  toResults: Airport[] = [];

  fromIATA: string = '';
  toIATA: string = '';
  constructor(
    private flightService: FlightServiceService,
    private router: Router,
    private http: HttpClient
    ) {
    const currentYear = new Date().getFullYear();
    this.minDate = new Date(currentYear);
    this.flight = new Flight("", "", new Date(), new Date(), 1);
  }

  get() {
    this.flight.from = this.fromTerm;
    this.flight.to = this.toTerm;
    this.flightService.get(new Flight(this.flight.from, this.flight.to, this.flight.arrivalDate, this.flight.departureDate, this.flight.amount))
    .subscribe(resp => this.gotoResults(resp));
  }

  gotoResults(result: Result) {
    this.router.navigate(['/results'], {state: { result }});
  }

  toggleArrival() {
    this.showArrival = !this.showArrival;
  }

  toggleDeparture() {
    this.showDeparture = !this.showDeparture;
  }

  searchFrom(): void {
    const params = new HttpParams()
      .set('search', this.fromTerm);

    this.http.get<Airport[]>('http://127.0.0.1:8080/api/v1/ticketmaster/search', {params : params}).subscribe(
      (response) => {
        this.fromResults = response;
      },
      (error) => {
        console.error(error);
      }
    );
  }

  searchTo(): void {
    const params = new HttpParams()
      .set('search', this.toTerm);

    this.http.get<Airport[]>('http://127.0.0.1:8080/api/v1/ticketmaster/search', {params : params}).subscribe(
      (response) => {
        this.toResults = response;
      },
      (error) => {
        console.error(error);
      }
    );
  }

  selectFrom(result: Airport): void {
    this.fromTerm = result.airport;
    this.fromIATA = result.iata;
    this.fromResults = [];
  }

  selectTo(result: Airport): void {
    this.toTerm = result.airport;
    this.toIATA = result.iata;
    this.toResults = [];
  }
}
