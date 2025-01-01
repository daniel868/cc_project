import {ROLE} from "../../../model/role";
import {createReducer, on} from "@ngrx/store";
import {GuestAuthAction, StoreAuthRoleAction} from "./auth.actions";

export interface AuthState {
  roles: ROLE[] | null,
  isGuest: boolean
}

const initialState: AuthState = {
  roles: [],
  isGuest: false
}

export const authReducer = createReducer(
  initialState,
  on(StoreAuthRoleAction, (state, {userRoles}) => {
    return {
      ...state,
      roles: userRoles
    }
  }),
  on(GuestAuthAction, (state, {guestValue}) => {
    return {
      ...state,
      isGuest: guestValue
    }
  })
)
