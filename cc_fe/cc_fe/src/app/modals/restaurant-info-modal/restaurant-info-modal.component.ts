import {Component, Input, OnInit} from '@angular/core';
import {Restaurant} from "../../model/restaurant";
import {BsModalRef} from "ngx-bootstrap/modal";

@Component({
  selector: 'app-restaurant-info-modal',
  templateUrl: './restaurant-info-modal.component.html',
  styleUrls: ['./restaurant-info-modal.component.scss']
})
export class RestaurantInfoModalComponent implements OnInit {

  @Input()
  restaurant: Restaurant

  constructor(private modalRef: BsModalRef) {
  }

  ngOnInit(): void {
  }

  onModalClose() {
    if (this.modalRef) {
      this.modalRef.hide();
    }
  }
}
