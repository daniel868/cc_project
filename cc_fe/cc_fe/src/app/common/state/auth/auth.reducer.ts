import {ROLE} from "../../../model/role";
import {createReducer, on} from "@ngrx/store";
import {GuestAuthAction, StoreAuthRoleAction} from "./auth.actions";

export interface AuthState {
  role: ROLE | null,
  isGuest: boolean
}

const initialState: AuthState = {
  role: null,
  isGuest: false
}

export const authReducer = createReducer(
  initialState,
  on(StoreAuthRoleAction, (state, {role}) => {
    return {
      ...state,
      role: role
    }
  }),
  on(GuestAuthAction, (state, {guestValue}) => {
    return {
      ...state,
      isGuest: guestValue
    }
  })
)
