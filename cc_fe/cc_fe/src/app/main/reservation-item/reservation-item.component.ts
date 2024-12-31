import {Component, Input, OnInit} from '@angular/core';
import {Reservation} from "../../model/reservation";

@Component({
  selector: 'app-reservation-item',
  templateUrl: './reservation-item.component.html',
  styleUrls: ['./reservation-item.component.scss']
})
export class ReservationItemComponent implements OnInit {

  @Input()
  reservation: Reservation;

  constructor() {
  }

  ngOnInit(): void {
  }

  onEditReservation(reservation: Reservation) {

  }

  onDeleteReservation(reservation: Reservation) {

  }

  onViewDetails(reservation: Reservation) {

  }
}
