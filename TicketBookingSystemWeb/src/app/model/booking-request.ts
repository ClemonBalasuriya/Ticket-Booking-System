export class BookingRequest {
    customer:any;
    numOfTickets: number =0;
    bookingService:any;

    // Setter for Customer
    setCustomer(customer: any): void {
        this.customer = customer;
    }

    setnumOfTickets(count: any): void {
        this.numOfTickets = count;
    }

}
