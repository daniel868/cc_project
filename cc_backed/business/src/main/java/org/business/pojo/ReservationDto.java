package org.business.pojo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Builder
public class ReservationDto {
    private int id;
    private String restaurantName;
    private Date reservationDate;
    private int guestCount;
    private String reservationGuestName;
}
