import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Result } from 'src/result';

@Component({
  selector: 'app-results',
  templateUrl: './results.component.html',
  styleUrls: ['./results.component.css'],
})
export class ResultsComponent implements OnInit {
  results!: Result[];
  showMore: boolean = false;

  constructor(private route: ActivatedRoute) {
    this.results = <Result[]>[
      {
        arrivalDate: new Date(),
        departureDate: new Date(),
        arrivalAirport: 'test',
        departureAirport: 'test',
        cost: 0,
        airline: 'test',
        flight_number: 0,
        stops: [],
        iata: 'test',
        amount: 0,
        showFlightDetails: false,
      },
      {
        arrivalDate: new Date(),
        departureDate: new Date(),
        arrivalAirport: 'test',
        departureAirport: 'test',
        cost: 0,
        airline: 'test',
        flight_number: 0,
        stops: [],
        iata: 'test',
        amount: 0,
      },
      {
        arrivalDate: new Date(),
        departureDate: new Date(),
        arrivalAirport: 'test',
        departureAirport: 'test',
        cost: 0,
        airline: 'test',
        flight_number: 0,
        stops: [],
        iata: 'test',
        amount: 0,
      },
      {
        arrivalDate: new Date(),
        departureDate: new Date(),
        arrivalAirport: 'test',
        departureAirport: 'test',
        cost: 0,
        airline: 'test',
        flight_number: 0,
        stops: [],
        iata: 'test',
        amount: 0,
      },
    ];
  }

  ngOnInit(): void {
    this.results = {
      ...history.state.results,
      arrivalDate: new Date(history.state.result.arrival_date).toLocaleString(),
      departureDate: new Date(history.state.result.departure_date).toLocaleString(),
    };
    console.log(this.results);
  }

  toggleShowMore() {
    this.showMore = !this.showMore;
  }

  toggleFlightDetails(flight: Result) {
    flight.showFlightDetails = !flight.showFlightDetails;
  }

}
