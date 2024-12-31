import {ActionReducerMap} from "@ngrx/store";
import {restaurantReducer, RestaurantState} from "./restaurant/restaurant.reducer";
import {authReducer, AuthState} from "./auth/auth.reducer";
import {reservationReducer, ReservationState} from "./reservation/reservation.reducer";

export interface AppState {
  restaurantState: RestaurantState,
  authState: AuthState,
  reservationsState: ReservationState
}

export const appReducer: ActionReducerMap<AppState> = {
  restaurantState: restaurantReducer,
  authState: authReducer,
  reservationsState: reservationReducer
}
