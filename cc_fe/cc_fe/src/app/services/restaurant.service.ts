import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../../environments/environment";
import {Observable} from "rxjs";
import {Restaurant} from "../model/restaurant";

@Injectable({
  providedIn: 'root'
})
export class RestaurantService {

  constructor(private httpClient: HttpClient) {
  }

  public getRestaurants(): Observable<any> {
    let url = environment.business_base_url + '/restaurants'
    return this.httpClient.get(url)
  }

  public addRestaurant(id: number, restaurant: Restaurant): Observable<any> {
    let url = environment.business_base_url + '/restaurants/add'
    let body = {
      "id": id,
      "name": restaurant.name,
      "owner": "Owner",
      "address": restaurant.address,
      "availableSpots": restaurant.availableSpots,
      // "maximumGuestNumber": restaurant.maximumGuestNumber,
      "imageUrl": restaurant.imageUrl
    }
    return this.httpClient.post(url, body)
  }
}
