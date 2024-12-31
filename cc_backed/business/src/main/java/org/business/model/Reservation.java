package org.business.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.service.customer.model.Customer;

import java.util.Date;

@Entity
@Table(name = "reservation")
@Getter
@Setter
@Data
public class Reservation {
    @Id
    @SequenceGenerator(name = "reservation_sequence_generator", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "reservation_sequence_generator")
    private int id;

    @JoinColumn(name = "customer_id")
    @OneToOne(fetch = FetchType.LAZY)
    private Customer customer;

    @Column(name = "guest_count")
    private int guestCount;

    @Column(name = "guest_name")
    private String guestName;

    @Column(name = "reservation_date")
    private Date reservationDate;

    @Column(name = "restaurant_name")
    private String restaurantName;


}