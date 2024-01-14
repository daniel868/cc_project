import {Component, Input, OnInit} from '@angular/core';
import {Restaurant} from "../../model/restaurant";

@Component({
  selector: 'app-restaurant-item',
  templateUrl: './restaurant-item.component.html',
  styleUrls: ['./restaurant-item.component.scss']
})
export class RestaurantItemComponent implements OnInit {

  @Input()
  restaurant: Restaurant = new Restaurant('test_name',
    'https://cdn.pixabay.com/photo/2022/01/28/18/32/leaves-6975462_1280.png',
    'description',
    'location_test');

  constructor() {
  }

  ngOnInit(): void {
  }

}
