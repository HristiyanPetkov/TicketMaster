import { Component } from '@angular/core';
import { Flight } from '../../flight';
import { FlightServiceService } from '../flight-service.service';
import { Result } from 'src/result';
import { Router } from '@angular/router';

@Component({
  selector: 'app-flight-form',
  templateUrl: './flight-form.component.html',
  styleUrls: ['./flight-form.component.css']
})
export class FlightFormComponent {
  minDate: Date;
  flight: Flight;
  constructor(
    private flightService: FlightServiceService,
    private router: Router
    ) {
    const currentYear = new Date().getFullYear();
    this.minDate = new Date(currentYear);
    this.flight = new Flight("", "", new Date(), new Date(), 1);
  }

  get() {
    this.flightService.get(new Flight(this.flight.from, this.flight.to, this.flight.arrivalDate, this.flight.departureDate, this.flight.ammount))
    .subscribe(resp => this.gotoResults(resp));
  }

  gotoResults(result: Result) {
    this.router.navigate(['/results'], {state: { result }});
  }
}
