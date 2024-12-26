package org.business.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "reservation")
@Getter
@Setter
public class Reservation {
    @Id
    @SequenceGenerator(name = "reservation_sequence_generator", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "reservation_sequence_generator")
    private int id;

    @Column(name = "customer_id")
    private int customerId;

    private int restaurantId;

    private int availableGuestSize;

    private Date reservationDate;

}