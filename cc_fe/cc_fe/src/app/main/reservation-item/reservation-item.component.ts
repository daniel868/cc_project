import {Component, Input, OnDestroy, OnInit} from '@angular/core';
import {Reservation} from "../../model/reservation";
import {BsModalService, ModalOptions} from "ngx-bootstrap/modal";
import {
  AddEditReservationModalComponent
} from "../../modals/add-edit-reservation-modal/add-edit-reservation-modal.component";
import {AppState} from "../../common/state/app.reducer";
import {Store} from "@ngrx/store";
import {DeleteReservationAction} from "../../common/state/reservation/reservation.actions";
import {Restaurant} from "../../model/restaurant";
import {filter, from, map, Subscription, switchMap, take, tap} from "rxjs";
import {RestaurantInfoModalComponent} from "../../modals/restaurant-info-modal/restaurant-info-modal.component";
import {StartFetchRestaurantByNameAction} from "../../common/state/restaurant/restaurant.actions";

@Component({
  selector: 'app-reservation-item',
  templateUrl: './reservation-item.component.html',
  styleUrls: ['./reservation-item.component.scss']
})
export class ReservationItemComponent implements OnInit, OnDestroy {

  @Input()
  reservation: Reservation;

  @Input()
  loadOnlyForCurrentCustomer: boolean;

  restaurant: Restaurant | null;

  restaurantSubscription: Subscription = new Subscription()

  constructor(private modalService: BsModalService,
              private store: Store<AppState>) {
  }

  ngOnInit(): void {
    this.restaurantSubscription = this.store.select('restaurantState')
      .pipe(
        map(response => response.restaurants?.payload || []),
        switchMap(restaurants => from(restaurants)),
        filter(restaurant => restaurant.name === this.reservation.restaurantName)
      ).subscribe(response => {
        this.restaurant = response;
      });
  }

  onEditReservation(reservation: Reservation) {
    if (!!this.reservation) {
      const initialState = {
        modalTitle: 'Edit Reservation',
        reservation: reservation,
        loadOnlyForCurrentCustomer: this.loadOnlyForCurrentCustomer
      };

      const modalOptions: ModalOptions = {
        initialState: initialState,
        backdrop: true,  // Enables backdrop click to close the modal
        keyboard: true,  // Close the modal when pressing escape
      };
      this.modalService.show(AddEditReservationModalComponent, modalOptions)
    }
  }

  onDeleteReservation(reservation: Reservation) {
    if (this.reservation && this.reservation.id) {
      this.store.dispatch(DeleteReservationAction({
        reservationId: this.reservation.id,
        loadOnlyForCurrentCustomer: this.loadOnlyForCurrentCustomer
      }))
    }
  }

  onViewDetails(restaurant: Restaurant | null) {
    if (restaurant === undefined) {
      this.store.dispatch(StartFetchRestaurantByNameAction({
        restaurantName: this.reservation.restaurantName,
        showRestaurantModalInfo: true
      }))
    } else {
      const initialState = {
        restaurant: restaurant,
        restaurantName: this.reservation.restaurantName
      };

      const modalOptions: ModalOptions = {
        initialState: initialState,
        backdrop: true,  // Enables backdrop click to close the modal
        keyboard: true,  // Close the modal when pressing escape
      };
      this.modalService.show(RestaurantInfoModalComponent, modalOptions);
    }
  };

  ngOnDestroy(): void {
    if (this.restaurantSubscription) {
      this.restaurantSubscription.unsubscribe();
    }
  }


}
