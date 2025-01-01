import {Component, OnInit} from '@angular/core';
import {NgForm} from '@angular/forms';
import {SignupInfo} from "../model/signup";
import {HttpClient} from "@angular/common/http";
import {environment} from "../../environments/environment";
import {Router} from "@angular/router";
import {GuestAuthAction, StartAuthRoleAction} from "../common/state/auth/auth.actions";
import {AppState} from "../common/state/app.reducer";
import {Store} from "@ngrx/store";

@Component({
  selector: 'app-auth',
  templateUrl: './auth.component.html',
  styleUrls: ['./auth.component.scss']
})
export class AuthComponent implements OnInit {

  constructor(private http: HttpClient,
              private router: Router,
              private store: Store<AppState>) {
  }

  ngOnInit(): void {
  }

  onSubmit(form: NgForm) {
    const signupInfo = new SignupInfo(
      form.value.username,
      form.value.password,
      form.value.email,
      form.value.firstname,
      form.value.lastname,
      form.value.type,
      form.value.phoneNumber
    );

    this.http.post<{ data: string }>(environment.register_url, signupInfo)
      .subscribe((response) => {
        console.log("Response: " + JSON.stringify(response))
        localStorage.setItem('jwt', response.data)
        this.store.dispatch(GuestAuthAction({guestValue: false}))
        this.store.dispatch(StartAuthRoleAction())
        this.router.navigate(['/main/restaurants']);

      }, error => {
        console.error(error);
      });
  }

  navigateToLogin() {
    this.router.navigate(['/login']);
  }
}
