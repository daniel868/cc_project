import {Component, EventEmitter, inject, OnDestroy, OnInit} from '@angular/core';
import {Restaurant} from "../../model/restaurant";
import {RestaurantService} from "../../services/restaurant.service";
import {AppState} from "../../common/state/app.reducer";
import {Store} from "@ngrx/store";
import {StartFetchRestaurantsAction} from "../../common/state/restaurant/restaurant.actions";
import {debounceTime, map, Subscription} from "rxjs";
import {PageEvent} from "@angular/material/paginator";
import {PageableRequest} from "../../common/shared/pageable-request";
import {BsModalRef, BsModalService, ModalOptions} from "ngx-bootstrap/modal";
import {
  AddEditRestaurantModalComponent
} from "../../modals/add-edit-restaurant-modal/add-edit-restaurant-modal.component";
import {Actions} from "@ngrx/effects";
import {environment} from "../../../environments/environment";

@Component({
  selector: 'app-restaurant',
  templateUrl: './restaurant.component.html',
  styleUrls: ['./restaurant.component.scss'],
})
export class RestaurantComponent implements OnInit, OnDestroy {

  totalElementsSize: number = 0;
  pageSize: number = 0;
  guestFilterNumber = -1;

  searchString = '';
  searchStringEventEmitter: EventEmitter<string> = new EventEmitter<string>();
  searchStringSubscription: Subscription = new Subscription();

  transformedData: Restaurant[] = [];

  constructor(private restaurantService: RestaurantService,
              private store: Store<AppState>,
              private modalService: BsModalService) {
  }

  ngOnInit(): void {
    this.store.dispatch(StartFetchRestaurantsAction({
      pagination: {
        page: environment.default_page_number,
        size: environment.default_page_size
      },
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

    this.searchStringSubscription = this.searchStringEventEmitter.pipe(
      debounceTime(300)
    ).subscribe(value => {
      this.store.dispatch(StartFetchRestaurantsAction({
        pagination: {
          page: environment.default_page_number,
          size: environment.default_page_size
        },
        searchString: this.searchString,
        guestFilterCount: this.guestFilterNumber
      }))
    })
  }

  onPageChanged($event: PageEvent) {
    let newPageSize = $event.pageSize
    let newPageNumber = $event.pageIndex;
    let newPageable: PageableRequest = {page: newPageNumber, size: newPageSize};

    this.store.dispatch(StartFetchRestaurantsAction({
      pagination: newPageable,
      searchString: this.searchString,
      guestFilterCount: this.guestFilterNumber
    }))
  }

  onGuestFilterChange($event: any) {
    this.store.dispatch(StartFetchRestaurantsAction({
      pagination: {
        page: environment.default_page_number,
        size: environment.default_page_size
      },
      searchString: this.searchString,
      guestFilterCount: this.guestFilterNumber
    }))
  }

  onSearchStringChange($event: any) {
    this.searchStringEventEmitter.emit(this.searchString);
  }

  ngOnDestroy(): void {
    if (this.searchStringSubscription) {
      this.searchStringSubscription.unsubscribe();
    }
  }


  showAddRestaurantModal() {
    const initialState = {
      modalTitle: 'Add Restaurant'
    };

    const modalOptions: ModalOptions = {
      initialState: initialState,
      backdrop: true,  // Enables backdrop click to close the modal
      keyboard: true,  // Close the modal when pressing escape
    };
    this.modalService.show(AddEditRestaurantModalComponent, modalOptions)
  }
}
