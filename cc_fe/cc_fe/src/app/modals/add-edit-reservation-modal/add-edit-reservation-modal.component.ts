import {Component, Input, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {Restaurant} from "../../model/restaurant";
import {BsModalRef} from "ngx-bootstrap/modal";
import {Reservation} from "../../model/reservation";
import {AppState} from "../../common/state/app.reducer";
import {Store} from "@ngrx/store";
import {AddReservationAction} from "../../common/state/reservation/reservation.actions";

@Component({
  selector: 'app-add-edit-reservation-modal',
  templateUrl: './add-edit-reservation-modal.component.html',
  styleUrls: ['./add-edit-reservation-modal.component.scss']
})
export class AddEditReservationModalComponent implements OnInit {

  @Input()
  restaurant: Restaurant

  @Input()
  modalTitle: string;

  addEditReservationForm: FormGroup;

  constructor(private modalRef: BsModalRef,
              private store: Store<AppState>) {
  }

  ngOnInit(): void {
    this.addEditReservationForm = new FormGroup({
      restaurantName: new FormControl({value: this.restaurant.name, disabled: true}),
      reservationDate: new FormControl('', [Validators.required]),
      guestCount: new FormControl(1, [Validators.required, Validators.min(1)]),
      reservationGuestName: new FormControl('', [Validators.required])
    })
  }

  onModalClose() {
    if (this.modalRef) {
      this.modalRef.hide()
    }
  }

  onSubmit() {
    let payload: Reservation = {
      id: null,
      restaurantName: this.getFormControl('restaurantName')?.value,
      reservationDate: this.getFormControl('reservationDate')?.value,
      reservationGuestName: this.getFormControl('reservationGuestName')?.value,
      guestCount: this.getFormControl('guestCount')?.value
    }

    if (!!this.restaurant.id) {
      this.store.dispatch(AddReservationAction({payload: payload, restaurantId: this.restaurant.id}))
    }

    this.onModalClose();
  }

  getFormControl(controlName: string) {
    return this.addEditReservationForm.get(controlName)
  }
}
