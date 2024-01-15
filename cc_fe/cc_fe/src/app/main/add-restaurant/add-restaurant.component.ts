import {Component, OnInit} from '@angular/core';
import {FormGroup} from "@angular/forms";
import {RestaurantService} from "../../services/restaurant.service";
import {Router} from "@angular/router";
import {Restaurant} from "../../model/restaurant";

@Component({
  selector: 'app-add-restaurant',
  templateUrl: './add-restaurant.component.html',
  styleUrls: ['./add-restaurant.component.scss']
})
export class AddRestaurantComponent implements OnInit {

  restaurantName: string = '';
  restaurantPictureUrl: string = '';
  restaurantAddress: string = '';
  restaurantDescription: string = '';
  restaurantGuestNumber: number = 0;

  constructor(private restaurantService: RestaurantService,
              private router: Router) {
  }

  ngOnInit(): void {
  }

  onSubmit() {
    let restaurant = new Restaurant(
      this.restaurantName,
      '',
      this.restaurantDescription,
      this.restaurantAddress
    );
    restaurant.maximumGuestNumber = this.restaurantGuestNumber;
    restaurant.availableSpots = this.restaurantGuestNumber * 2;


  }

  onCancel() {
    this.router.navigate(['/main'])
  }

  onImageChange(value: string) {
    console.log("image chnageg")
    this.restaurantPictureUrl = value;
  }

}
