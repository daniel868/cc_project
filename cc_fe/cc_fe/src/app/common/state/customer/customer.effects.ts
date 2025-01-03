import {inject, Injectable} from "@angular/core";
import {Actions, createEffect, ofType} from "@ngrx/effects";
import {HttpClient, HttpParams} from "@angular/common/http";
import {FinishFetchReservationAction, StartFetchReservationAction} from "../reservation/reservation.actions";
import {catchError, exhaustMap, map, of, switchMap} from "rxjs";
import {PageableGenericResponse} from "../../shared/pageable-generic-response";
import {Reservation} from "../../../model/reservation";
import {environment} from "../../../../environments/environment";
import {GenericFailedAction, GenericSuccessAction} from "../shared/shared.actions";
import {FinishFetchCurrentCustomer, StartFetchCurrentCustomer, UpdateCustomerAction} from "./customer.actions";
import {Customer} from "../../../model/customer";

@Injectable()
export class CustomerEffects {
  private actions$ = inject(Actions)

  constructor(private httpClient: HttpClient) {
  }

  fetchCurrentCustomerEffect = createEffect(() =>
    this.actions$.pipe(
      ofType(StartFetchCurrentCustomer),
      switchMap(props => {
        return this.httpClient.get<Customer>(`${environment.business_base_url}/customers/currentCustomer`)
          .pipe(
            map(data => {
              return FinishFetchCurrentCustomer({customer: data})
            }),
            catchError(err => {
              return of(GenericFailedAction({message: "Error while fetching customers"}))
            })
          )
      })
    )
  )

  updateCustomerEffect = createEffect(() =>
    this.actions$.pipe(
      ofType(UpdateCustomerAction),
      switchMap(props => {
        return this.httpClient.put<Boolean>(`${environment.business_base_url}/customers/${props.id}`, props.customer)
          .pipe(
            exhaustMap(response => {
              let resultAction = response ?
                GenericSuccessAction({message: "Success updated profile information"}) :
                GenericFailedAction({message: "Failed updating profile information"})
              return [
                resultAction,
                StartFetchCurrentCustomer()
              ]
            }),
            catchError(error => {
              return of(GenericFailedAction({message: "Failed updating profile information"}));
            })
          )
      })
    )
  )

}
