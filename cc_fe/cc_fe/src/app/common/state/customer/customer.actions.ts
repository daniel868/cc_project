import {createAction, props} from "@ngrx/store";
import {ROLE} from "../../../model/role";
import {Customer} from "../../../model/customer";

export const StartFetchCurrentCustomer = createAction('[Customer] Start Fetch Current Customer');

export const FinishFetchCurrentCustomer = createAction('[Customer] Finish Fetch Current Customer', props<{
  customer: Customer
}>());
