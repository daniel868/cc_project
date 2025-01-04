import {PageableGenericResponse} from "../../shared/pageable-generic-response";
import {Restaurant} from "../../../model/restaurant";
import {createReducer, on} from "@ngrx/store";
import {FinishFetchRestaurantByNameAction, FinishFetchRestaurantsAction} from "./restaurant.actions";

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
  }),
  on(FinishFetchRestaurantByNameAction, (state, {restaurant}) => {
    console.log("New Restaurant" + JSON.stringify(restaurant))
    if (!!state.restaurants) {
      return {
        ...state,
        restaurants: {
          ...state.restaurants,
          payload: [...state.restaurants.payload, restaurant],
        }
      }
    } else {
      return {
        ...state,
        restaurants: {
          payload: [restaurant],
          currentPage: 0,
          totalPageCount: 1,
          pageSize: 1,
          nextPage: 0,
          totalElementsCount: 1,
        },
      };
    }
  })
)

