import { Vendor } from "./vendor";

export class IssuingRequest {
    vendor:any;
    numOfTickets: number =0;
    bookingService:any;

    setVendor(vendor: any): void {
        this.vendor = vendor;
    }

    setnumOfTickets(count: any): void {
        this.numOfTickets = count;
    }

   
}
