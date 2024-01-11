package com.example.business_logic.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Date;
import java.sql.Time;

@Entity
@Table(name = "reservation", schema = "restaurant_reservations")
public class Reservation {
@Id
private int id;

private int customerId;

private int restaurantId;

@DateTimeFormat(pattern = "dd-MM-yyyy")
@Column(name = "reservation_date")
private Date reservationDate;

private Time reservationTime;

private int guestNumber;

    public Reservation(int id, int customerId, int restaurantId, Date reservationDate, Time reservationTime, int guestNumber) {
        this.id = id;
        this.customerId = customerId;
        this.restaurantId = restaurantId;
        this.reservationDate = reservationDate;
        this.reservationTime = reservationTime;
        this.guestNumber = guestNumber;
    }

    public Reservation() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(int restaurantId) {
        this.restaurantId = restaurantId;
    }

    public Date getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(Date reservationDate) {
        this.reservationDate = reservationDate;
    }

    public Time getReservationTime() {
        return reservationTime;
    }

    public void setReservationTime(Time reservationTime) {
        this.reservationTime = reservationTime;
    }

    public int getGuestNumber() {
        return guestNumber;
    }

    public void setGuestNumber(int guestNumber) {
        this.guestNumber = guestNumber;
    }
}
