export interface Reservation {
  id: number | null,
  restaurantName: string,
  reservationDate: Date,
  reservationGuestName: string,
  guestCount: number
}

/*
  {
    "id": 1,
    "restaurantName": "restaurant2",
    "reservationDate": "2024-12-27T21:44:46.940+00:00",
    "guestCount": 10,
    "reservationGuestName": "guestName2"
  }
 */
