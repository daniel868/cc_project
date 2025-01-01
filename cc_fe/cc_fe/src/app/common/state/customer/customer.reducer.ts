import {Customer} from "../../../model/customer";
import {createReducer, on} from "@ngrx/store";
import {FinishFetchCurrentCustomer} from "./customer.actions";

export interface CustomerState {
  customer: Customer | null
}

const initialState: CustomerState = {
  customer: null
}

export const customerReducer = createReducer(
  initialState,
  on(FinishFetchCurrentCustomer, (state, {customer}) => {
    return {
      ...state,
      customer: customer
    }
  })
);

