import {ActionReducerMap} from "@ngrx/store";
import {restaurantReducer, RestaurantState} from "./restaurant/restaurant.reducer";

export interface AppState {
  restaurantState: RestaurantState
}

export const appReducer: ActionReducerMap<AppState> = {
  restaurantState: restaurantReducer
}
