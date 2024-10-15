import {Component} from '@angular/core';
import {NgForm} from '@angular/forms';
import {HttpClient} from '@angular/common/http';
import {Router} from '@angular/router';
import {LoginData} from "../model/login";
import {environment} from "../../environments/environment";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {

  constructor(private http: HttpClient, private router: Router) {
  }

  onLogin(form: NgForm) {
    const loginData = new LoginData(form.value.username, form.value.password);
    this.http.post(environment.login_url, loginData)
      .subscribe((response: any) => {
        console.log(response);
        localStorage.setItem('jwt', response.jwt);
        this.router.navigate(['/main']);
      }, error => {
        console.error(error);
      });
  }

  onRegisterRedirect() {
    this.router.navigate(['/auth']);
  }
}
