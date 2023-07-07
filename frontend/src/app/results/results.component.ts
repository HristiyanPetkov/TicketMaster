import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Result } from 'src/result';

@Component({
  selector: 'app-results',
  templateUrl: './results.component.html',
  styleUrls: ['./results.component.css'],
})
export class ResultsComponent implements OnInit {
  result!: Result;

  constructor(private route: ActivatedRoute) {}

  ngOnInit(): void {
    this.result = {
      ...history.state.result,
      arrivalDate: new Date(history.state.result.arrival_date).toLocaleString(),
      departureDate: new Date(history.state.result.departure_date).toLocaleString(),
    };
    console.log(this.result);
  }
}
