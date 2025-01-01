import {inject, Injectable} from "@angular/core";
import {Actions, createEffect, ofType} from "@ngrx/effects";
import {exhaustMap, map, switchMap} from "rxjs";
import {Router} from "@angular/router";
import {GuestAuthAction, LogoutAction, StartAuthRoleAction, StoreAuthRoleAction} from "./auth.actions";
import {HttpClient} from "@angular/common/http";
import {ROLE} from "../../../model/role";

@Injectable()
export class AuthEffects {
  private actions$ = inject(Actions)

  constructor(private router: Router,
              private httpClient: HttpClient) {
  }

  logoutAuthEffect = createEffect(() =>
    this.actions$.pipe(
      ofType(LogoutAction),
      exhaustMap((response) => {
        localStorage.removeItem('jwt');
        localStorage.removeItem('isGuest')
        this.router.navigate(['/login'])
        return [
          GuestAuthAction({guestValue: true}),
          StoreAuthRoleAction({userRoles: []})
        ]
      })
    )
  )

  guestAuthAction = createEffect(() =>
    this.actions$.pipe(
      ofType(GuestAuthAction),
      map(response => {
        localStorage.setItem('isGuest', JSON.stringify(response))
      })
    ), {dispatch: false}
  )

  startFetchAuthRole = createEffect(() =>
    this.actions$.pipe(
      ofType(StartAuthRoleAction),
      switchMap(response => {
        return this.httpClient.get<ROLE[]>(`http://localhost:9001/api/roles`).pipe(
          map(response => {
            return StoreAuthRoleAction({userRoles: response})
          })
        )
      })
    )
  )
}
