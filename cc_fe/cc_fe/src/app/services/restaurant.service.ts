import {Injectable} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {environment} from "../../environments/environment";
import {Observable} from "rxjs";

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
}
