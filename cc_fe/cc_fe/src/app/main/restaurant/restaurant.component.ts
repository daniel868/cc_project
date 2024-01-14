import {Component, OnInit} from '@angular/core';
import {Restaurant} from "../../model/restaurant";
import {RestaurantService} from "../../services/restaurant.service";

@Component({
  selector: 'app-restaurant',
  templateUrl: './restaurant.component.html',
  styleUrls: ['./restaurant.component.scss']
})
export class RestaurantComponent implements OnInit {

  restaurants: Restaurant[] = [
    new Restaurant('test_name',
      'https://cdn.pixabay.com/photo/2022/01/28/18/32/leaves-6975462_1280.png',
      'description',
      'location_test'),
    new Restaurant('test_name',
      'https://cdn.pixabay.com/photo/2022/01/28/18/32/leaves-6975462_1280.png',
      'description',
      'location_test')
  ]

  constructor(private restaurantService: RestaurantService) {
  }

  ngOnInit(): void {
    this.restaurantService.getRestaurants()
      .subscribe((response) => {
        console.log(response)
      });
  }

}
