import {Component, OnInit} from '@angular/core';
import {AppState} from "../common/state/app.reducer";
import {Store} from "@ngrx/store";
import {map} from "rxjs";
import {LogoutAction} from "../common/state/auth/auth.actions";
import {AuthService} from "../services/auth.service";
import {BsModalService} from "ngx-bootstrap/modal";
import {CurrentCustomerModalComponent} from "../modals/current-customer-modal/current-customer-modal.component";

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {

  loggedAsGuest: boolean;
  authenticate: boolean;

  constructor(private store: Store<AppState>,
              private authService: AuthService,
              private modalService: BsModalService) {
  }

  ngOnInit(): void {
    this.store.select('authState').pipe(
      map(response => {
        return response.isGuest
      })
    ).subscribe(loggedAsGuest => {
      this.loggedAsGuest = loggedAsGuest;
      this.authenticate = this.authService.isAuthenticated()
    });

  }

  onLogout() {
    this.store.dispatch(LogoutAction())
  }


  onProfileClick() {
    this.modalService.show(CurrentCustomerModalComponent)
  }
}
