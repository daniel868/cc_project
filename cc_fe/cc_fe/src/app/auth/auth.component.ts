import { Component, OnInit } from '@angular/core';
import { NgForm } from '@angular/forms';
import {SignupInfo} from "../model/signup";
import {HttpClient} from "@angular/common/http";
import {environment} from "../../environments/environment";
import {Router} from "@angular/router";

@Component({
  selector: 'app-auth',
  templateUrl: './auth.component.html',
  styleUrls: ['./auth.component.scss']
})
export class AuthComponent implements OnInit {

  constructor(private http: HttpClient, private router: Router) {}

  ngOnInit(): void {
  }

  onSubmit(form: NgForm) {
    const signupInfo = new SignupInfo(
      form.value.username,
      form.value.password,
      form.value.email,
      form.value.firstname,
      form.value.lastname,
      form.value.type
    );

    this.http.post(environment.register_url, signupInfo)
      .subscribe(response => {
        console.log(response);
        this.router.navigate(['/main']);
      }, error => {
        console.error(error);
      });
  }

  navigateToLogin() {
    this.router.navigate(['/login']);
  }
}
