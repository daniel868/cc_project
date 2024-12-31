import {Component, OnInit} from '@angular/core';
import {AppState} from "../../common/state/app.reducer";
import {Store} from "@ngrx/store";
import {StartFetchReservationAction} from "../../common/state/reservation/reservation.actions";
import {environment} from "../../../environments/environment.prod";
import {map} from "rxjs";
import {Restaurant} from "../../model/restaurant";
import {Reservation} from "../../model/reservation";
import {PageEvent} from "@angular/material/paginator";

@Component({
  selector: 'app-reservation',
  templateUrl: './reservation.component.html',
  styleUrls: ['./reservation.component.scss']
})
export class ReservationComponent implements OnInit {
  totalElementsSize: number = 0;
  pageSize: number = 0;
  transformedData: Reservation[] = [];

  constructor(private store: Store<AppState>) {
  }

  ngOnInit(): void {
    this.store.dispatch(StartFetchReservationAction({
      pageable: {
        page: environment.default_page_number,
        size: environment.default_page_size
      },
      searchString: ''
    }));

    this.store.select('reservationsState').pipe(
      map(reservationState => reservationState.reservations)
    ).subscribe(data => {
      if (!!data) {
        this.transformedData = data?.payload
        this.pageSize = data.pageSize
        this.totalElementsSize = data.totalElementsCount
      }

    })
  }

  onPageChanged($event: PageEvent) {
    let newPageSize = $event.pageSize;
    let newPageIndex = $event.pageIndex;

    this.store.dispatch(StartFetchReservationAction({
      pageable: {
        page: newPageIndex,
        size: newPageSize
      },
      searchString: ''
    }))
  }
}
