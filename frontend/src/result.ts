export class Result {
  constructor(
    public departureAirport: string,
    public arrivalAirport: string,
    public departureDate: Date,
    public arrivalDate: Date,
    public amount: number,
    public cost: number,
    public stops: string[],
    public flight_number: number,
    public airline: string,
    public showFlightDetails: boolean = false,
  ) { }
}
