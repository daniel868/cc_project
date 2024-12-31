import {PageableGenericResponse} from "../../shared/pageable-generic-response";
import {Reservation} from "../../../model/reservation";
import {createReducer, on} from "@ngrx/store";
import {FinishFetchReservationAction} from "./reservation.actions";

export interface ReservationState {
  reservations: PageableGenericResponse<Reservation> | null
}

const initialState: ReservationState = {
  reservations: null
}

export const reservationReducer = createReducer(
  initialState,
  on(FinishFetchReservationAction, (state, {response}) => {
    return {
      ...state,
      reservations: response
    }
  })
)
