import {inject, Injectable} from "@angular/core";
import {Actions, createEffect, ofType} from "@ngrx/effects";
import {exhaustMap, map, of, switchMap, tap} from "rxjs";
import {Router} from "@angular/router";
import {GuestAuthAction, LogoutAction} from "./auth.actions";

@Injectable()
export class AuthEffects {
  private actions$ = inject(Actions)

  constructor(private router: Router) {
  }

  logoutAuthEffect = createEffect(() =>
    this.actions$.pipe(
      ofType(LogoutAction),
      map((response) => {
        localStorage.removeItem('jwt');
        this.router.navigate(['/login'])
        return GuestAuthAction({guestValue: true})
      })
    )
  )
}