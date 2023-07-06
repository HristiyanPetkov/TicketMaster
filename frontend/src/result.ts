export class Result {
  constructor(
    public departure: string,
    public arrival: string,
    public date: Date,
    public ammount: number,
    public cost: number,
    public iota: string,
    public stops: string[],
    public flight_number: number
  ) { }
}
