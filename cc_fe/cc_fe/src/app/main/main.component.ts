import {Component, OnDestroy, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {ROLE} from "../model/role";
import {Store} from "@ngrx/store";
import {AppState} from "../common/state/app.reducer";
import {map, Subscription} from "rxjs";
import {state} from "@angular/animations";
import {AuthService} from "../services/auth.service";
import {StartFetchCurrentCustomer} from "../common/state/customer/customer.actions";

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrls: ['./main.component.scss']
})
export class MainComponent implements OnInit, OnDestroy {

  isAdmin: boolean = false;

  adminSubscription: Subscription = new Subscription();

  constructor(private store: Store<AppState>,
              private authService: AuthService) {
  }


  ngOnInit(): void {
    this.adminSubscription = this.store.select('authState')
      .pipe(
        map(state => state.roles)
      ).subscribe(roles => {
        if (!!roles) {
          this.isAdmin = !!roles.find(role => role.roleName === 'ADMIN')
        }
      });

    if (this.authService.isAuthenticated()){
      this.store.dispatch(StartFetchCurrentCustomer())
    }
  }

  ngOnDestroy(): void {
    if (this.adminSubscription) {
      this.adminSubscription.unsubscribe();
    }
  }


}
