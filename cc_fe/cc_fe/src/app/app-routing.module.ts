import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {AuthComponent} from "./auth/auth.component";
import {MainComponent} from "./main/main.component";
import {AddRestaurantComponent} from "./main/add-restaurant/add-restaurant.component";
import {RestaurantComponent} from "./main/restaurant/restaurant.component";
import {LoginComponent} from "./login/login.component";
import {HTTP_INTERCEPTORS} from "@angular/common/http";
import {TokenInterceptor} from "./token-interceptor.interceptor";
import {AuthGuard} from "./common/auth.guard";

const routes: Routes = [
  {
    path: '',
    redirectTo: '/main',
    pathMatch: 'full'
  },
  {
    path: 'auth',
    component: AuthComponent
  },
  {
    path: 'login',
    component: LoginComponent
  },
  {
    path: 'main',
    component: MainComponent,
    canActivate: [AuthGuard],
    children: [
      {
        path: 'add-restaurant',
        component: AddRestaurantComponent
      },
      {
        path: 'restaurants',
        component: RestaurantComponent
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
  providers: [
    {
      provide: HTTP_INTERCEPTORS,
      useClass: TokenInterceptor,
      multi: true
    }
  ]
})
export class AppRoutingModule {
}
