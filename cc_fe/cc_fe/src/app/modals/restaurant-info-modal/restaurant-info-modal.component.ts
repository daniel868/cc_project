import {Component, Input, OnInit} from '@angular/core';
import {Restaurant} from "../../model/restaurant";
import {BsModalRef} from "ngx-bootstrap/modal";
import {StartFetchRestaurantByNameAction} from "../../common/state/restaurant/restaurant.actions";
import {AppState} from "../../common/state/app.reducer";
import {Store} from "@ngrx/store";

@Component({
  selector: 'app-restaurant-info-modal',
  templateUrl: './restaurant-info-modal.component.html',
  styleUrls: ['./restaurant-info-modal.component.scss']
})
export class RestaurantInfoModalComponent implements OnInit {

  @Input()
  restaurant: Restaurant

  @Input()
  restaurantName: string

  constructor(private modalRef: BsModalRef,
              private store: Store<AppState>) {
  }

  ngOnInit(): void {

  }

  onModalClose() {
    if (this.modalRef) {
      this.modalRef.hide();
    }
  }
}
