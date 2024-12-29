import {inject, Injectable} from "@angular/core";
import {Actions, createEffect, ofType} from "@ngrx/effects";
import {HttpClient, HttpParams} from "@angular/common/http";
import {
  AddRestaurantAction, DeleteRestaurantAction,
  EditRestaurantAction,
  FinishFetchRestaurantsAction,
  StartFetchRestaurantsAction
} from "./restaurant.actions";
import {catchError, exhaustMap, map, of, switchMap, tap} from "rxjs";
import {PageableGenericResponse} from "../../shared/pageable-generic-response";
import {Restaurant} from "../../../model/restaurant";
import {environment} from "../../../../environments/environment";
import {BsModalService, ModalOptions} from "ngx-bootstrap/modal";
import {GenericSuccessModalComponent} from "../../../modals/generic-success-modal/generic-success-modal.component";
import {GenericFailedAction, GenericSuccessAction} from "../shared/shared.actions";

@Injectable()
export class RestaurantEffects {
  private actions$ = inject(Actions)

  constructor(private httpClient: HttpClient) {

  }

  fetchRestaurantEffect = createEffect(() =>
    this.actions$.pipe(
      ofType(StartFetchRestaurantsAction),
      tap(() => console.log("Start fetching restaurants from DB")),
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
              return of(GenericFailedAction({message: "Error occurred fetching restaurants"}))
            })
          )
      })
    )
  )

  addRestaurantEffect = createEffect(() =>
    this.actions$.pipe(
      ofType(AddRestaurantAction),
      tap(() => console.log("Start adding new restaurant")),
      switchMap((actionProps) => {
        return this.httpClient.post<Restaurant>(`${environment.business_base_url}/restaurants`, actionProps.payload).pipe(
          exhaustMap(response => {
            let resultAction = response.id ?
              GenericSuccessAction({message: "Success adding restaurant " + response.name}) :
              GenericFailedAction({message: "Error occurred while updating restaurant"});
            return [
              resultAction,
              StartFetchRestaurantsAction({
                pagination: {
                  page: environment.default_page_number,
                  size: environment.default_page_size
                }, searchString: '', guestFilterCount: -1
              })

            ]
          }),
          catchError((error) => {
            return of(GenericFailedAction({message: "Error occurred while updating restaurant"}))
          })
        )
      })
    )
  )

  editRestaurantEffect = createEffect(() =>
    this.actions$.pipe(
      ofType(EditRestaurantAction),
      tap(() => console.log("Start editing new restaurant")),
      switchMap((actionProps) => {
        return this.httpClient.put<boolean>(`${environment.business_base_url}/restaurants/${actionProps.id}`, actionProps.payload)
          .pipe(
            exhaustMap(response => {
              let resultAction = response ?
                GenericSuccessAction({message: "Success updated restaurant"}) :
                GenericFailedAction({message: "Error occurred while updating restaurant"});

              return [
                resultAction,
                StartFetchRestaurantsAction({
                  pagination: {
                    page: environment.default_page_number,
                    size: environment.default_page_size
                  }, searchString: '', guestFilterCount: -1
                })
              ]
            }),
            catchError(error => {
              return of(GenericFailedAction({message: "Error occurred while updating restaurant"}))
            })
          )
      })
    )
  )

  deleteRestaurantEffect = createEffect(() =>
    this.actions$.pipe(
      ofType(DeleteRestaurantAction),
      switchMap(actionProps => {
        return this.httpClient.delete<boolean>(`${environment.business_base_url}/restaurants/${actionProps.restaurantId}`)
          .pipe(
            exhaustMap(response => {
              let resultAction = response ?
                GenericSuccessAction({message: "Success deleted restaurant"}) :
                GenericFailedAction({message: "Error occurred while deleting restaurant"});

              return [
                resultAction,
                StartFetchRestaurantsAction({
                    pagination: {
                      page: environment.default_page_number,
                      size: environment.default_page_size
                    }, searchString: '', guestFilterCount: -1
                  }
                )
              ]
            }),
            catchError(error => {
              return of(GenericFailedAction({message: "Error occurred while deleting restaurant"}))
            })
          )
      })
    )
  )

}
