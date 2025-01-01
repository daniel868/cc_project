import {Component, Input, OnInit} from '@angular/core';
import {Restaurant} from "../../model/restaurant";
import {BsModalService, ModalOptions} from "ngx-bootstrap/modal";
import {RestaurantInfoModalComponent} from "../../modals/restaurant-info-modal/restaurant-info-modal.component";
import {
  AddEditRestaurantModalComponent
} from "../../modals/add-edit-restaurant-modal/add-edit-restaurant-modal.component";
import {AppState} from "../../common/state/app.reducer";
import {Store} from "@ngrx/store";
import {DeleteRestaurantAction} from "../../common/state/restaurant/restaurant.actions";
import {
  AddEditReservationModalComponent
} from "../../modals/add-edit-reservation-modal/add-edit-reservation-modal.component";

@Component({
  selector: 'app-restaurant-item',
  templateUrl: './restaurant-item.component.html',
  styleUrls: ['./restaurant-item.component.scss']
})
export class RestaurantItemComponent implements OnInit {

  @Input()
  restaurant: Restaurant | null = null

  @Input()
  isAdmin: boolean = false;

  constructor(private modalService: BsModalService,
              private store: Store<AppState>) {
  }

  ngOnInit(): void {
  }

  onDetailsViewClicked(restaurant: Restaurant | null) {
    if (!!restaurant) {
      const initialState = {
        restaurant: restaurant
      };

      const modalOptions: ModalOptions = {
        initialState: initialState,
        backdrop: true,  // Enables backdrop click to close the modal
        keyboard: true,  // Close the modal when pressing escape
      };
      this.modalService.show(RestaurantInfoModalComponent, modalOptions);
    }
  }

  onEditRestaurantClicked(restaurant: Restaurant | null) {
    if (!!restaurant) {
      const initialState = {
        modalTitle: 'Edit Restaurant',
        editRestaurant: restaurant
      };

      const modalOptions: ModalOptions = {
        initialState: initialState,
        backdrop: true,  // Enables backdrop click to close the modal
        keyboard: true,  // Close the modal when pressing escape
      };
      this.modalService.show(AddEditRestaurantModalComponent, modalOptions)
    }
  }

  onDeleteRestaurant(restaurant: Restaurant | null) {
    if (!!restaurant && !!restaurant.id) {
      this.store.dispatch(DeleteRestaurantAction({restaurantId: restaurant.id}))
    }
  }

  onBookClicked() {
    const initialState = {
      modalTitle: 'Add Reservation',
      restaurant: this.restaurant
    }
    const modalOptions: ModalOptions = {
      initialState: initialState,
      backdrop: true,  // Enables backdrop click to close the modal
      keyboard: true,  // Close the modal when pressing escape
    };
    this.modalService.show(AddEditReservationModalComponent, modalOptions)
  }
}
