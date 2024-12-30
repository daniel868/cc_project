import {Injectable, OnInit} from '@angular/core';
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree} from '@angular/router';
import {map, Observable} from 'rxjs';
import {AuthService} from "../services/auth.service";
import {AppState} from "./state/app.reducer";
import {select, Store} from "@ngrx/store";

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate, OnInit {

  loggedAsGuest: boolean = false

  constructor(private authService: AuthService,
              private router: Router,
              private store: Store<AppState>) {
  }

  ngOnInit(): void {

  }


  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    return this.store.select('authState')
      .pipe(
        map(response => {
          if (this.authService.isAuthenticated() || response.isGuest) {
            return true;
          }
          return this.router.createUrlTree(['/login']);
        })
      );
  }

}
