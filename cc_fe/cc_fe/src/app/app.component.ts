import {Component, OnInit} from '@angular/core';
import {AppState} from "./common/state/app.reducer";
import {Store} from "@ngrx/store";
import {GuestAuthAction, StartAuthRoleAction} from "./common/state/auth/auth.actions";
import {AuthService} from "./services/auth.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {
  title = 'cc_fe';

  constructor(private store: Store<AppState>,
              private authService: AuthService) {
  }

  ngOnInit(): void {
    let response: { guestValue: boolean };
    let guestObj = localStorage.getItem('isGuest');
    if (guestObj) {
      response = JSON.parse(guestObj)
      this.store.dispatch(GuestAuthAction(response))
    }
    if (this.authService.isAuthenticated()) {
      this.store.dispatch(StartAuthRoleAction())
    }
  }

}
