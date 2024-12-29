import {Component, Input, OnInit} from '@angular/core';
import {BsModalRef} from "ngx-bootstrap/modal";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {AppState} from "../../common/state/app.reducer";
import {Store} from "@ngrx/store";
import {Restaurant} from "../../model/restaurant";
import {AddRestaurantAction, EditRestaurantAction} from "../../common/state/restaurant/restaurant.actions";

@Component({
  selector: 'app-add-edit-restaurant-modal',
  templateUrl: './add-edit-restaurant-modal.component.html',
  styleUrls: ['./add-edit-restaurant-modal.component.scss']
})
export class AddEditRestaurantModalComponent implements OnInit {

  @Input()
  modalTitle: string

  @Input()
  editRestaurant: Restaurant

  addEditRestaurantForm: FormGroup;

  constructor(private modalRef: BsModalRef,
              private store: Store<AppState>) {
    this.addEditRestaurantForm = new FormGroup({
      restaurantName: new FormControl('', [Validators.required]),
      restaurantPictureUrl: new FormControl('', [Validators.required]),
      restaurantDescription: new FormControl(''),
      restaurantAddress: new FormControl('', [Validators.required]),
      maximGuestNumber: new FormControl(0, [
        Validators.required,
        Validators.pattern("^[1-9]+[0-9]*$")])
    });
  }

  ngOnInit(): void {
    if (!!this.editRestaurant) {
      this.getFormControl('restaurantName')?.setValue(this.editRestaurant.name)
      this.getFormControl('restaurantPictureUrl')?.setValue(this.editRestaurant.imageUrl)
      this.getFormControl('restaurantDescription')?.setValue(this.editRestaurant.description)
      this.getFormControl('restaurantAddress')?.setValue(this.editRestaurant.address)
      this.getFormControl('maximGuestNumber')?.setValue(this.editRestaurant.availableSpots)
    }
  }

  onSubmit() {
    let payload: Restaurant = {
      name: this.getFormControl('restaurantName')?.value,
      address: this.getFormControl('restaurantAddress')?.value,
      availableSpots: this.getFormControl('maximGuestNumber')?.value,
      imageUrl: this.getFormControl('restaurantPictureUrl')?.value,
      description: this.getFormControl('restaurantDescription')?.value,
      id: null
    }

    if (!!this.editRestaurant && !!this.editRestaurant.id) {
      this.store.dispatch(EditRestaurantAction({payload: payload, id: this.editRestaurant.id}))
    } else {
      this.store.dispatch(AddRestaurantAction({payload: payload}))
    }


    this.onModalClose()
  }

  onModalClose() {
    if (this.modalRef) {
      this.modalRef.hide()
    }
  }

  getFormControl(controlName: string) {
    return this.addEditRestaurantForm.get(controlName)
  }
}
