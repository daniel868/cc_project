import {createAction, props} from "@ngrx/store";
import {PageableRequest} from "../../shared/pageable-request";
import {PageableGenericResponse} from "../../shared/pageable-generic-response";
import {Restaurant} from "../../../model/restaurant";

export const StartFetchRestaurantsAction = createAction('[Restaurant] Start Fetch Restaurants', props<{
  pagination: PageableRequest,
  searchString: string,
  guestFilterCount: number
}>());

export const FinishFetchRestaurantsAction = createAction('[Restaurant] Finish Fetch Restaurants', props<{
  response: PageableGenericResponse<Restaurant>
}>());

export const StartFetchRestaurantByNameAction = createAction('[Restaurant] Start Fetch By Name', props<{
  restaurantName: string,
  showRestaurantModalInfo: boolean
}>());

export const FinishFetchRestaurantByNameAction = createAction('[Restaurant] Finish Fetch By Name', props<{
  restaurant: Restaurant
}>());

export const ShowRestaurantInfoModal = createAction('[Restaurant] Show Restaurant Info Modal ', props<{
  restaurant: Restaurant
}>());

export const AddRestaurantAction = createAction('[Restaurant] Add Restaurant', props<{
  payload: Restaurant
}>());

export const EditRestaurantAction = createAction('[Restaurant] Edit Restaurant', props<{
  payload: Restaurant,
  id: number
}>());


export const DeleteRestaurantAction = createAction('[Restaurant] Delete Restaurant', props<{
  restaurantId: number
}>());
