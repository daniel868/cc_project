package org.business.pojo;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReservationDto {
    private int id;
    private String restaurantName;
    private Date reservationDate;
    private int guestCount;
    private String reservationGuestName;
}
