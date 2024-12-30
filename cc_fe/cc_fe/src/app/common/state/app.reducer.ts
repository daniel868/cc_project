import {ActionReducerMap} from "@ngrx/store";
import {restaurantReducer, RestaurantState} from "./restaurant/restaurant.reducer";
import {authReducer, AuthState} from "./auth/auth.reducer";

export interface AppState {
  restaurantState: RestaurantState,
  authState: AuthState
}

export const appReducer: ActionReducerMap<AppState> = {
  restaurantState: restaurantReducer,
  authState: authReducer
}
