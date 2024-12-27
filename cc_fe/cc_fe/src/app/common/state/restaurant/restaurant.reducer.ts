import {PageableGenericResponse} from "../../shared/pageable-generic-response";
import {Restaurant} from "../../../model/restaurant";
import {createReducer, on} from "@ngrx/store";
import {FinishFetchRestaurantsAction} from "./restaurant.actions";

export interface RestaurantState {
  restaurants: PageableGenericResponse<Restaurant> | null
}

const initialState: RestaurantState = {
  restaurants: null
}

export const restaurantReducer = createReducer(
  initialState,
  on(FinishFetchRestaurantsAction, (state, {response}) => {
    return {
      ...state,
      restaurants: response
    }
  })
)

