import {NgModule} from '@angular/core';
import {BrowserModule} from '@angular/platform-browser';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {AuthComponent} from './auth/auth.component';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {MainComponent} from './main/main.component';
import {NgOptimizedImage} from "@angular/common";
import {RestaurantItemComponent} from './main/restaurant-item/restaurant-item.component';
import {RestaurantComponent} from './main/restaurant/restaurant.component';
import {HeaderComponent} from './header/header.component';
import {DropdownDirective} from "./dropdown.directive";
import {HttpClientModule} from "@angular/common/http";
import {LoginComponent} from './login/login.component';
import {ReservationComponent} from './main/reservation/reservation.component';
import {StoreModule} from "@ngrx/store";
import {appReducer} from "./common/state/app.reducer";
import {EffectsModule} from "@ngrx/effects";
import {RestaurantEffects} from "./common/state/restaurant/restaurant.effects";
import {MatPaginatorModule} from "@angular/material/paginator";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {AddEditRestaurantModalComponent} from './modals/add-edit-restaurant-modal/add-edit-restaurant-modal.component';
import {BsModalRef, BsModalService} from "ngx-bootstrap/modal";
import {BsDropdownConfig} from "ngx-bootstrap/dropdown";
import {RestaurantInfoModalComponent} from './modals/restaurant-info-modal/restaurant-info-modal.component';
import {GenericSuccessModalComponent} from './modals/generic-success-modal/generic-success-modal.component';
import {GenericFailedModalComponent} from './modals/generic-failed-modal/generic-failed-modal.component';
import {SharedEffects} from "./common/state/shared/shared.effects";
import {AuthEffects} from "./common/state/auth/auth.effects";
import {
  AddEditReservationModalComponent
} from './modals/add-edit-reservation-modal/add-edit-reservation-modal.component';
import {ReservationEffects} from "./common/state/reservation/reservation.effects";
import {ReservationItemComponent} from './main/reservation-item/reservation-item.component';
import {CustomerEffects} from "./common/state/customer/customer.effects";
import { CurrentCustomerModalComponent } from './modals/current-customer-modal/current-customer-modal.component';

@NgModule({
  declarations: [
    AppComponent,
    AuthComponent,
    MainComponent,
    RestaurantItemComponent,
    RestaurantComponent,
    HeaderComponent,
    DropdownDirective,
    LoginComponent,
    ReservationComponent,
    AddEditRestaurantModalComponent,
    RestaurantInfoModalComponent,
    GenericSuccessModalComponent,
    GenericFailedModalComponent,
    AddEditReservationModalComponent,
    ReservationItemComponent,
    CurrentCustomerModalComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule,
    NgOptimizedImage,
    StoreModule.forRoot(appReducer),
    EffectsModule.forRoot([RestaurantEffects, SharedEffects, AuthEffects, ReservationEffects, CustomerEffects]),
    MatPaginatorModule,
    BrowserAnimationsModule
  ],
  providers: [
    BsModalService,
    BsModalRef,
    BsDropdownConfig
  ],
  bootstrap: [AppComponent]
})
export class AppModule {
}
