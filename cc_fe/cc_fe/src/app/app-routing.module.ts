import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {AuthComponent} from "./auth/auth.component";
import {MainComponent} from "./main/main.component";
import {AddRestaurantComponent} from "./main/add-restaurant/add-restaurant.component";

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
    path: 'main',
    component: MainComponent,
    children: [
      {
        path: 'change-restaurant',
        component: AddRestaurantComponent
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
