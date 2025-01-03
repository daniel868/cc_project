import {createAction, props} from "@ngrx/store";
import {Restaurant} from "../../../model/restaurant";
import {Reservation} from "../../../model/reservation";
import {PageableRequest} from "../../shared/pageable-request";
import {PageableGenericResponse} from "../../shared/pageable-generic-response";

export const StartFetchReservationAction = createAction('[Reservation] Start Fetch Reservation', props<{
  pageable: PageableRequest,
  searchString: string,
  searchDate: number | null
}>())

export const FinishFetchReservationAction = createAction('[Reservation] Finish Fetch Reservation', props<{
  response: PageableGenericResponse<Reservation>
}>())

export const AddReservationAction = createAction('[Reservation] Add Reservation', props<{
  payload: Reservation,
  restaurantId: number
}>())

export const UpdateReservationAction = createAction('[Reservation] Update Reservation', props<{
  payload: Reservation,
  reservationId: number
}>())

export const DeleteReservationAction = createAction('[Reservation] Delete Reservation', props<{
  reservationId: number
}>())
