import {Component, OnInit} from '@angular/core';
import {FormGroup} from "@angular/forms";

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

  constructor() {
  }

  ngOnInit(): void {
  }

  onSubmit() {

  }

  onCancel() {

  }

  onImageChange(value: string) {
    console.log("image chnageg")
    this.restaurantPictureUrl = value;
  }

}
