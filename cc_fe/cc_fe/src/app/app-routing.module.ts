import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {AuthComponent} from "./auth/auth.component";
import {MainComponent} from "./main/main.component";
import {AddRestaurantComponent} from "./main/add-restaurant/add-restaurant.component";
import {RestaurantComponent} from "./main/restaurant/restaurant.component";

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
  exports: [RouterModule]
})
export class AppRoutingModule {
}
