import { Component, HostListener } from '@angular/core';
import { Flight } from '../../flight';
import { FlightServiceService } from '../flight-service.service';
import { Result } from 'src/result';
import { Router } from '@angular/router';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable, map } from 'rxjs';
import { Airport } from 'src/airport';
import { environment } from 'src/environment/environment';

@Component({
  selector: 'app-flight-form',
  templateUrl: './flight-form.component.html',
  styleUrls: ['./flight-form.component.css'],
})
export class FlightFormComponent {
  minDate: Date;
  flight: Flight;
  showArrival = true;
  showDeparture = true;
  fromTerm = '';
  fromResults: Airport[] = [];
  showFromResults = true;

  toTerm = '';
  toResults: Airport[] = [];
  showToResults = true;

  fromIATA = '';
  toIATA = '';

  currentOption = 'departure';
  switchPosition = 0; // in pixels

  constructor(
    private flightService: FlightServiceService,
    private router: Router,
    private http: HttpClient
  ) {
    const currentYear = new Date().getFullYear();
    this.minDate = new Date(currentYear);
    this.flight = new Flight('', '', new Date(), new Date(), 1);
  }

  get() {
    this.flight.from = this.fromTerm;
    this.flight.to = this.toTerm;
    this.flightService
      .get(
        new Flight(
          this.flight.from,
          this.flight.to,
          this.flight.arrivalDate,
          this.flight.departureDate,
          this.flight.amount
        )
      )
      .subscribe((resp) => this.gotoResults(resp));
  }

  gotoResults(results: Result[]) {
    this.router.navigate(['/results'], { state: { results } });
  }

  toggleArrival() {
    this.showArrival = !this.showArrival;
  }

  toggleDeparture() {
    this.showDeparture = !this.showDeparture;
  }

  searchFrom(): void {
    this.toggleAutocompleteFrom();
    const params = new HttpParams().set('search', this.fromTerm);

    this.http
      .get<Airport[]>(`${environment.flightUrl}/search`, {
        params: params,
      })
      .subscribe(
        (response) => {
          this.fromResults = response;
        },
        (error) => {
          console.error(error);
        }
      );
  }

  searchTo(): void {
    this.toggleAutocompleteTo();
    const params = new HttpParams().set('search', this.toTerm);

    this.http
      .get<Airport[]>('http://127.0.0.1:8080/api/v1/ticketmaster/search', {
        params: params,
      })
      .subscribe(
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
    this.fromResults = <Airport[]>(<unknown>result);
  }

  selectTo(result: Airport): void {
    this.toTerm = result.airport;
    this.toIATA = result.iata;
    this.toResults = <Airport[]>(<unknown>result);
  }

  toggleAutocompleteFrom() {
    this.showFromResults = true;
  }

  toggleAutocompleteTo() {
    this.showToResults = true;
  }

  @HostListener('document:click', ['$event.target'])
  onClickOutside(target: any) {
    const fromInput = document.getElementById('from');
    if (!fromInput) {
      return;
    }

    if (!fromInput.contains(target)) {
      this.showFromResults = false;
    }

    const toInput = document.getElementById('to');
    if (!toInput) {
      return;
    }

    if (!toInput.contains(target)) {
      this.showToResults = false;
    }
  }

  switchOption() {
    if (this.currentOption === 'departure') {
      this.currentOption = 'arrival';
      this.switchPosition = 12; // you will need to adjust this value according to your needs
    } else if (this.currentOption === 'arrival') {
      this.currentOption = 'both';
      this.switchPosition = 24; // you will need to adjust this value according to your needs
    } else if (this.currentOption === 'both') {
      this.currentOption = 'departure';
      this.switchPosition = 0;
    }
  }
}
