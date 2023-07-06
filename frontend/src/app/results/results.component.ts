import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Result } from 'src/result';

@Component({
  selector: 'app-results',
  templateUrl: './results.component.html',
  styleUrls: ['./results.component.css']
})
export class ResultsComponent implements OnInit {
  result!: Result;

  constructor(private reoute:ActivatedRoute) {}

  ngOnInit(): void {
      this.result = history.state.result;
      console.log(this.result);
  }
}
