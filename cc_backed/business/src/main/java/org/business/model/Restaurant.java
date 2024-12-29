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
    @SequenceGenerator(name = "restaurant_id_sequence_generator", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "restaurant_id_sequence_generator")
    private int id;

    private String name;

    private String address;

    private int availableSpots;

    @Column(name = "image_url")
    private String imageUrl;

    private String description;

    @OneToMany(fetch = FetchType.LAZY, cascade = {
            CascadeType.PERSIST,
            CascadeType.REMOVE
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
