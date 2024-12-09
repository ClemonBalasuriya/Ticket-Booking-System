import { Routes } from '@angular/router';
import { ConfigComponent } from './component/config/config.component';
import { LoginComponent } from './component/login/login.component';
import { TicketbookingComponent } from './component/ticketbooking/ticketbooking.component';
import { RegistrationComponent } from './component/registration/registration.component';

export const routes: Routes = [
    {path:'',component:ConfigComponent},
    {path:'login',component:LoginComponent},
    {path:'ticketbooking', component:TicketbookingComponent},
    {path:'registration', component:RegistrationComponent},
    {path:'ticketrelease', component:RegistrationComponent}
];
