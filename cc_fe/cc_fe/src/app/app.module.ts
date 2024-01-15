import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {AuthComponent} from './auth/auth.component';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {MainComponent} from './main/main.component';
import {AddRestaurantComponent} from './main/add-restaurant/add-restaurant.component';
import {NgOptimizedImage} from "@angular/common";
import {RestaurantItemComponent} from './main/restaurant-item/restaurant-item.component';
import {RestaurantComponent} from './main/restaurant/restaurant.component';
import {HeaderComponent} from './header/header.component';
import {DropdownDirective} from "./dropdown.directive";
import {HttpClient, HttpClientModule} from "@angular/common/http";
import { LoginComponent } from './login/login.component';
import { ReservationComponent } from './main/reservation/reservation.component';

@NgModule({
  declarations: [
    AppComponent,
    AuthComponent,
    MainComponent,
    AddRestaurantComponent,
    RestaurantItemComponent,
    RestaurantComponent,
    HeaderComponent,
    DropdownDirective,
    LoginComponent,
    ReservationComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    NgOptimizedImage
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}
