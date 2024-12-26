package org.business.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "restaurant")
@Getter
@Setter
public class Restaurant {
    @Id
    //@GeneratedValue(strategy = GenerationType.AUTO)
    //@GeneratedValue(generator = "uuid")
    private int id;

    private String name;

    private String owner;

    private String address;

    private int availableSpots;

    @Column(name = "image_url")
    private String imageUrl;

    @OneToMany(fetch = FetchType.LAZY, cascade = {
            CascadeType.PERSIST
    })
    @JoinColumn(name = "restaurant_id")
    private Set<Reservation> reservations;

    public void addReservation(Reservation reservation) {
        if (reservations == null) {
            reservations = new HashSet<>();
        }
        reservations.add(reservation);
    }


}
