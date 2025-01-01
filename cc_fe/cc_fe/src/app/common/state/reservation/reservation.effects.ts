import {inject, Injectable} from "@angular/core";
import {Actions, createEffect, ofType} from "@ngrx/effects";
import {
  AddReservationAction,
  DeleteReservationAction,
  FinishFetchReservationAction,
  StartFetchReservationAction,
  UpdateReservationAction
} from "./reservation.actions";
import {catchError, exhaustMap, map, of, switchMap} from "rxjs";
import {HttpClient, HttpParams} from "@angular/common/http";
import {Reservation} from "../../../model/reservation";
import {environment} from "../../../../environments/environment";
import {GenericFailedAction, GenericSuccessAction} from "../shared/shared.actions";
import {PageableGenericResponse} from "../../shared/pageable-generic-response";

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
        if (!!props.searchDate) {
          queryParams = queryParams.append('searchDate', props.searchDate)
        }
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

  updateReservationEffect = createEffect(() =>
    this.actions$.pipe(
      ofType(UpdateReservationAction),
      switchMap(props => {
        return this.httpClient.put<boolean>(`${environment.business_base_url}/reservations/${props.reservationId}`,
          props.payload
        ).pipe(
          exhaustMap(response => {
            let resultAction = response ?
              GenericSuccessAction({message: "Successfully updated the reservation"}) :
              GenericFailedAction({message: "Error occurred while updating reservation"})
            return [
              resultAction,
              StartFetchReservationAction({
                pageable: {
                  size: environment.default_page_size,
                  page: environment.default_page_number
                },
                searchString: '',
                searchDate: null
              })
            ]
          }),
          catchError(error => {
            return of(GenericFailedAction({message: "Error occurred while updating reservation"}))
          })
        )
      })
    )
  )

  deleteReservationEffect = createEffect(() =>
    this.actions$.pipe(
      ofType(DeleteReservationAction),
      switchMap(props => {
        return this.httpClient.delete<boolean>(`${environment.business_base_url}/reservations/${props.reservationId}`).pipe(
          exhaustMap(response => {
            let resultAction = response ?
              GenericSuccessAction({message: "Successfully cancel the reservation"}) :
              GenericFailedAction({message: "Error occurred while canceling reservation"})
            return [
              resultAction,
              StartFetchReservationAction({
                pageable: {
                  size: environment.default_page_size,
                  page: environment.default_page_number
                },
                searchString: '',
                searchDate: null
              })
            ]
          }),
          catchError(error => {
            return of(GenericFailedAction({message: "Error occurred while canceling reservation"}))
          })
        )
      })
    )
  )
}
