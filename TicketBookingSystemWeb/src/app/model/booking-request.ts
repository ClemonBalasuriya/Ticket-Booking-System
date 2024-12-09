export class BookingRequest {
    customer:any;
    numOfTickets: number =0;
    bookingService:any;

    // Setter for Customer
    setCustomer(customer: any): void {
        this.customer = customer;
    }

    // Setter for Customer
    setnumOfTickets(customer: any): void {
        this.numOfTickets = customer;
    }

}
