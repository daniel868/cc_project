import {Component, OnInit} from '@angular/core';
import {AppState} from "./common/state/app.reducer";
import {Store} from "@ngrx/store";
import {GuestAuthAction} from "./common/state/auth/auth.actions";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {
  title = 'cc_fe';

  constructor(private store: Store<AppState>) {
  }

  ngOnInit(): void {
    let response: { guestValue: boolean };
    let guestObj = localStorage.getItem('isGuest');
    if (guestObj) {
      response = JSON.parse(guestObj)
      console.log("object from session " + JSON.stringify(response))
      this.store.dispatch(GuestAuthAction(response))
    }

  }

}
