export class Flight{
  constructor(
    public from: string,
    public to: string,
    public arrivalDate: Date,
    public departureDate: Date,
    public amount: number
  ) { }
}
