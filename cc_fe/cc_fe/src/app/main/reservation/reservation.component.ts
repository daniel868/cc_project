import {Component, EventEmitter, OnDestroy, OnInit} from '@angular/core';
import {AppState} from "../../common/state/app.reducer";
import {Store} from "@ngrx/store";
import {StartFetchReservationAction} from "../../common/state/reservation/reservation.actions";
import {environment} from "../../../environments/environment.prod";
import {debounceTime, map, Subscription} from "rxjs";
import {Reservation} from "../../model/reservation";
import {PageEvent} from "@angular/material/paginator";

@Component({
  selector: 'app-reservation',
  templateUrl: './reservation.component.html',
  styleUrls: ['./reservation.component.scss']
})
export class ReservationComponent implements OnInit, OnDestroy {
  searchString: string = '';
  searchDate: Date | null = null;
  longSearchDate: number | null = null;

  totalElementsSize: number = 0;
  pageSize: number = 0;
  transformedData: Reservation[] = [];

  searchStringEventEmitter: EventEmitter<string> = new EventEmitter<string>();
  searchStringSubscription = new Subscription();

  constructor(private store: Store<AppState>) {
  }

  ngOnInit(): void {
    this.store.dispatch(StartFetchReservationAction({
      pageable: {
        page: environment.default_page_number,
        size: environment.default_page_size
      },
      searchString: '',
      searchDate: null
    }));

    this.store.select('reservationsState').pipe(
      map(reservationState => reservationState.reservations)
    ).subscribe(data => {
      if (!!data) {
        this.transformedData = data?.payload
        this.pageSize = data.pageSize
        this.totalElementsSize = data.totalElementsCount
      }
    });

    this.searchStringSubscription = this.searchStringEventEmitter.pipe(
      debounceTime(300)
    ).subscribe(response => {
      this.store.dispatch(StartFetchReservationAction({
        pageable: {
          page: environment.default_page_number,
          size: environment.default_page_size
        },
        searchString: this.searchString,
        searchDate: this.longSearchDate
      }));
    });
  }

  onPageChanged($event: PageEvent) {
    let newPageSize = $event.pageSize;
    let newPageIndex = $event.pageIndex;

    this.store.dispatch(StartFetchReservationAction({
      pageable: {
        page: newPageIndex,
        size: newPageSize
      },
      searchString: this.searchString,
      searchDate: this.longSearchDate
    }));
  }

  onSearchChanged($event: any) {
    this.searchStringEventEmitter.emit(this.searchString);
  }

  onDateChanged($event: any) {
    if (this.searchDate) {
      this.longSearchDate = new Date(this.searchDate.toString()).getTime()
    } else {
      this.longSearchDate = null
    }

    this.store.dispatch(StartFetchReservationAction({
      pageable: {
        page: environment.default_page_number,
        size: environment.default_page_size
      },
      searchString: this.searchString,
      searchDate: this.longSearchDate
    }));
  }

  ngOnDestroy(): void {
    if (this.searchStringSubscription) {
      this.searchStringSubscription.unsubscribe();
    }
  }


}
