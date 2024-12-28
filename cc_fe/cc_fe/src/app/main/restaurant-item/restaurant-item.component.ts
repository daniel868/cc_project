import {Component, Input, OnInit} from '@angular/core';
import {Restaurant} from "../../model/restaurant";

@Component({
  selector: 'app-restaurant-item',
  templateUrl: './restaurant-item.component.html',
  styleUrls: ['./restaurant-item.component.scss']
})
export class RestaurantItemComponent implements OnInit {

  @Input()
  restaurant: Restaurant | null = null

  constructor() {
  }

  ngOnInit(): void {
  }

}
