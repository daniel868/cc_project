import {Component, Input, OnInit} from '@angular/core';
import {Customer} from "../../model/customer";
import {Store} from "@ngrx/store";
import {AppState} from "../../common/state/app.reducer";
import {filter, from, map, switchMap} from "rxjs";
import {BsModalRef} from "ngx-bootstrap/modal";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {UpdateCustomerAction} from "../../common/state/customer/customer.actions";
import {ROLE} from "../../model/role";

@Component({
  selector: 'app-current-customer-modal',
  templateUrl: './current-customer-modal.component.html',
  styleUrls: ['./current-customer-modal.component.scss']
})
export class CurrentCustomerModalComponent implements OnInit {

  @Input()
  customer: Customer;

  customerModalForm: FormGroup;

  role: ROLE;

  constructor(private store: Store<AppState>,
              private modalRef: BsModalRef) {
  }

  ngOnInit(): void {
    this.store.select('customerState')
      .pipe(
        map(customerState => {
          return customerState.customer
        })
      ).subscribe(response => {
      if (!!response) {
        this.customer = response;
      }
    });

    this.store.select('authState')
      .pipe(
        map(response => {
          return response.roles || []
        }),
        switchMap(restaurants => from(restaurants)),
      ).subscribe(response => {
      this.role = response;
    })

    this.customerModalForm = new FormGroup({
      userName: new FormControl(this.customer.name, [Validators.required]),
      phoneNumber: new FormControl(this.customer.phoneNumber, [Validators.required]),
      email: new FormControl(this.customer.emailAddress, [Validators.email]),
      role: new FormControl({value: this.role.roleName, disabled: true})
    })
  }

  onModalClose() {
    if (this.modalRef) {
      this.modalRef.hide();
    }
  }

  onCustomerUpdate() {
    let payload: Customer = {
      name: this.getFormControl('userName')?.value,
      phoneNumber: this.getFormControl('phoneNumber')?.value,
      emailAddress: this.getFormControl('email')?.value,
      id: this.customer.id
    }

    this.store.dispatch(UpdateCustomerAction({customer: payload, id: this.customer.id}));
    this.onModalClose();
  }

  getFormControl(controlName: string) {
    return this.customerModalForm.get(controlName)
  }
}
