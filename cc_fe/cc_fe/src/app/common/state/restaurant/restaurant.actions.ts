import {createAction, props} from "@ngrx/store";
import {PageableRequest} from "../../shared/pageable-request";
import {PageableGenericResponse} from "../../shared/pageable-generic-response";
import {Restaurant} from "../../../model/restaurant";

export const StartFetchRestaurantsAction = createAction('[Restaurant] Start Fetch Restaurants', props<{
  pagination: PageableRequest,
  searchString: string,
  guestFilterCount: number
}>())

export const FinishFetchRestaurantsAction = createAction('[Restaurant] Finish Fetch Restaurants', props<{
  response: PageableGenericResponse<Restaurant>
}>())
