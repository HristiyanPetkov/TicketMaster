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
  }

  ngOnInit(): void {
    this.results = history.state.results.map((result: any) => {
      return new Result(
        result.departureAirport,
        result.arrivalAirport,
        new Date(result.departure_date),
        new Date(result.arrival_date),
        0,
        result.price,
        result.stops,
        result.flightNumber,
        result.airline,
      );
    });

    console.log(this.results);
  }

  toggleShowMore() {
    this.showMore = !this.showMore;
  }

  toggleFlightDetails(flight: Result) {
    flight.showFlightDetails = !flight.showFlightDetails;
  }

}
