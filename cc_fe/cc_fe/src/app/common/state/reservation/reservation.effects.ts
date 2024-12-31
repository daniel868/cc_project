import {inject, Injectable} from "@angular/core";
import {Actions, createEffect, ofType} from "@ngrx/effects";
import {AddReservationAction, FinishFetchReservationAction, StartFetchReservationAction} from "./reservation.actions";
import {catchError, exhaustMap, map, of, switchMap} from "rxjs";
import {HttpClient, HttpParams} from "@angular/common/http";
import {Restaurant} from "../../../model/restaurant";
import {Reservation} from "../../../model/reservation";
import {environment} from "../../../../environments/environment";
import {GenericFailedAction, GenericSuccessAction} from "../shared/shared.actions";
import {PageableGenericResponse} from "../../shared/pageable-generic-response";
import {FinishFetchRestaurantsAction} from "../restaurant/restaurant.actions";

@Injectable()
export class ReservationEffects {
  private actions$ = inject(Actions)

  constructor(private httpClient: HttpClient) {
  }

  fetchReservationsEffect = createEffect(() =>
    this.actions$.pipe(
      ofType(StartFetchReservationAction),
      switchMap(props => {
        let queryParams = new HttpParams()
          .append('page', props.pageable.page)
          .append('size', props.pageable.size)
          .append('searchString', props.searchString)
        return this.httpClient.get<PageableGenericResponse<Reservation>>(`${environment.business_base_url}/reservations`, {params: queryParams})
          .pipe(
            map(data => {
              return FinishFetchReservationAction({response: data})
            }),
            catchError(err => {
              return of(GenericFailedAction({message: "Error fetching reservations"}))
            })
          )
      })
    )
  )

  addReservationEffect = createEffect(() =>
    this.actions$.pipe(
      ofType(AddReservationAction),
      switchMap(props => {
        return this.httpClient.post<Reservation>(`${environment.business_base_url}/reservations/${props.restaurantId}`,
          props.payload
        ).pipe(
          exhaustMap(response => {
            let resultAction = response.id ?
              GenericSuccessAction({message: "Successfully booked"}) :
              GenericFailedAction({message: "Error occurred while booking reservation"})
            return [
              resultAction
            ]
          }),
          catchError(error => {
            return of(GenericFailedAction({message: "Error occurred while booking reservation"}))
          })
        )
      })
    )
  )
}
