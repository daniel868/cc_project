package org.business.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.service.customer.model.Customer;

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

    @JoinColumn(name = "customer_id")
    @OneToOne(fetch = FetchType.LAZY)
    private Customer customer;

    private int guestCount;

    private String guestName;

    private Date reservationDate;

    private String restaurantName;

}