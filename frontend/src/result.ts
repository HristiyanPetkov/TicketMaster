export class Result {
  constructor(
    public departureAirport: string,
    public arrivalAirport: string,
    public departureDate: Date,
    public arrivalDate: Date,
    public ammount: number,
    public cost: number,
    public iota: string,
    public stops: string[],
    public flight_number: number
  ) { }
}
