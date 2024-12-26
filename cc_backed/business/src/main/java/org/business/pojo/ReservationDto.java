package org.business.pojo;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class ReservationDto {
    private String restaurantName;
    private Date reservationDate;
    private int guestCount;
    private String reservationGuestName;
}
