import {Component, OnInit} from '@angular/core';
import {Restaurant} from "../../model/restaurant";
import {RestaurantService} from "../../services/restaurant.service";
import {AppState} from "../../common/state/app.reducer";
import {Store} from "@ngrx/store";
import {StartFetchRestaurantsAction} from "../../common/state/restaurant/restaurant.actions";
import {map} from "rxjs";
import {PageEvent} from "@angular/material/paginator";
import {PageableRequest} from "../../common/shared/pageable-request";

@Component({
  selector: 'app-restaurant',
  templateUrl: './restaurant.component.html',
  styleUrls: ['./restaurant.component.scss'],
})
export class RestaurantComponent implements OnInit {
  totalElementsSize: number = 0;
  pageSize: number = 0;
  guestFilterNumber = -1;

  transformedData: Restaurant[] = [];

  constructor(private restaurantService: RestaurantService,
              private store: Store<AppState>) {
  }

  ngOnInit(): void {
    this.store.dispatch(StartFetchRestaurantsAction({
      pagination: {page: 0, size: 10},
      searchString: '',
      guestFilterCount: this.guestFilterNumber
    }))

    this.store.select('restaurantState')
      .pipe(
        map(response => {
          return response.restaurants
        })
      ).subscribe(data => {
      if (!!data) {
        this.transformedData = data.payload
        this.pageSize = data.pageSize
        this.totalElementsSize = data.totalElementsCount
      }
    })
  }

  onPageChanged($event: PageEvent) {
    let newPageSize = $event.pageSize
    let newPageNumber = $event.pageIndex;
    let newPageable: PageableRequest = {page: newPageNumber, size: newPageSize};

    this.store.dispatch(StartFetchRestaurantsAction({
      pagination: newPageable,
      searchString: '',
      guestFilterCount: this.guestFilterNumber
    }))
  }

  onGuestFilterChange($event: any) {
    this.store.dispatch(StartFetchRestaurantsAction({
      pagination: {page: 0, size: 10},
      searchString: '',
      guestFilterCount: this.guestFilterNumber
    }))
  }
}
