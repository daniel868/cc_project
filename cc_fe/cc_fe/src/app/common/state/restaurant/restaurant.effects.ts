import {inject, Injectable} from "@angular/core";
import {Actions, createEffect, ofType} from "@ngrx/effects";
import {HttpClient, HttpParams} from "@angular/common/http";
import {FinishFetchRestaurantsAction, StartFetchRestaurantsAction} from "./restaurant.actions";
import {catchError, map, of, switchMap, tap} from "rxjs";
import {PageableGenericResponse} from "../../shared/pageable-generic-response";
import {Restaurant} from "../../../model/restaurant";
import {environment} from "../../../../environments/environment";

@Injectable()
export class RestaurantEffects {
  private actions$ = inject(Actions)

  constructor(private httpClient: HttpClient) {

  }

  fetchRestaurantEffect = createEffect(() =>
    this.actions$.pipe(
      tap(() => console.log("Start fetching restaurants from DB")),
      ofType(StartFetchRestaurantsAction),
      switchMap((actionProps) => {
        let queryParams = new HttpParams()
          .append('page', actionProps.pagination.page)
          .append('size', actionProps.pagination.size)
          .append('searchString', actionProps.searchString)
          .append('guestCountFilter', actionProps.guestFilterCount)
        return this.httpClient.get<PageableGenericResponse<Restaurant>>(`${environment.business_base_url}/restaurants`, {params: queryParams})
          .pipe(
            map(response => {
              return FinishFetchRestaurantsAction({response: response})
            }),
            catchError((error) => {
              return of()
            })
          )
      })
    )
  )
}
