export interface PageableGenericResponse<T> {
  payload: T[],
  currentPage: number,
  totalPageCount: number,
  pageSize: number,
  nextPage: number
}

// {
//   "payload": [
//   {
//     "id": 1,
//     "restaurantName": "restaurant2",
//     "reservationDate": "2024-12-27T21:44:46.940+00:00",
//     "guestCount": 10,
//     "reservationGuestName": "guestName2"
//   }
// ],
//   "currentPage": 0,
//   "totalPageCount": 1,
//   "pageSize": 1,
//   "nextPage": 0
// }
