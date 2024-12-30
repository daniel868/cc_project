import {createAction, props} from "@ngrx/store";
import {ROLE} from "../../../model/role";


export const StoreAuthRoleAction = createAction('[Auth] Store Role Auth', props<{
  role: ROLE
}>())

export const GuestAuthAction = createAction('[Auth] Store Guest Auth', props<{
  guestValue: boolean
}>())

export const LogoutAction = createAction('[Auth] Logout Action')
