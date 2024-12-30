import {Component} from '@angular/core';
import {NgForm} from '@angular/forms';
import {HttpClient} from '@angular/common/http';
import {Router} from '@angular/router';
import {LoginData} from "../model/login";
import {environment} from "../../environments/environment";
import {AppState} from "../common/state/app.reducer";
import {Store} from "@ngrx/store";
import {
  GuestAuthAction,
  StoreAuthRoleAction
} from "../common/state/auth/auth.actions";
import {ROLE} from "../model/role";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {

  constructor(private http: HttpClient,
              private router: Router,
              private store: Store<AppState>) {
  }

  onLogin(form: NgForm) {
    const loginData = new LoginData(form.value.username, form.value.password);
    this.http.post(environment.login_url, loginData)
      .subscribe((response: any) => {
        console.log(response);
        localStorage.setItem('jwt', response.jwt);
        this.router.navigate(['/main/restaurants']);
        this.store.dispatch(GuestAuthAction({guestValue: false}))
      }, error => {
        console.error(error);
      });
  }

  onRegisterRedirect() {
    this.router.navigate(['/auth']);
  }

  onContinueAsGuest() {
    this.store.dispatch(StoreAuthRoleAction({role: ROLE.ADMIN}))
    this.store.dispatch(GuestAuthAction({guestValue: true}))
    this.router.navigate(['/main/restaurants']);
  }
}
