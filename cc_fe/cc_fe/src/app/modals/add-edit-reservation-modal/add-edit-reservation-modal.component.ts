import {Component, Input, OnInit} from '@angular/core';
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {Restaurant} from "../../model/restaurant";
import {BsModalRef} from "ngx-bootstrap/modal";
import {Reservation} from "../../model/reservation";
import {AppState} from "../../common/state/app.reducer";
import {Store} from "@ngrx/store";
import {AddReservationAction, UpdateReservationAction} from "../../common/state/reservation/reservation.actions";

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

  @Input()
  reservation: Reservation

  addEditReservationForm: FormGroup;

  constructor(private modalRef: BsModalRef,
              private store: Store<AppState>) {
  }

  ngOnInit(): void {
    let restaurantName = !!this.restaurant ?
      this.restaurant.name :
      this.reservation.restaurantName
    this.addEditReservationForm = new FormGroup({
      restaurantName: new FormControl({value: restaurantName, disabled: true}),
      reservationDate: new FormControl('', [Validators.required]),
      guestCount: new FormControl(1, [Validators.required, Validators.min(1)]),
      reservationGuestName: new FormControl('', [Validators.required])
    })

    if (!!this.reservation) {
      this.getFormControl('restaurantName')?.setValue(this.reservation.restaurantName)
      this.getFormControl('reservationDate')?.setValue(this.reservation.reservationDate.toString().slice(0, 16))
      this.getFormControl('guestCount')?.setValue(this.reservation.guestCount)
      this.getFormControl('reservationGuestName')?.setValue(this.reservation.reservationGuestName)
    }
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

    if (!!this.restaurant && !!this.restaurant.id) {
      this.store.dispatch(AddReservationAction({payload: payload, restaurantId: this.restaurant.id}))
    }

    if (!!this.reservation && !!this.reservation.id) {
      this.store.dispatch(UpdateReservationAction({payload: payload, reservationId: this.reservation.id}))
    }

    this.onModalClose();
  }

  getFormControl(controlName: string) {
    return this.addEditReservationForm.get(controlName)
  }
}
