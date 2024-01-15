package com.example.business_logic.model;

import jakarta.persistence.*;

@Entity
@Table(name = "restaurant", schema = "restaurant_reservations")
public class Restaurant {
    @Id
    //@GeneratedValue(strategy = GenerationType.AUTO)
    //@GeneratedValue(generator = "uuid")
    private int id;

    private String name;

    private String owner;

    private String address;

    private int availableSpots;

    private int maximumGuestNumber;

    @Column(name = "image_url")
    private String imageUrl;

    public int getId() {
        return id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getAvailableSpots() {
        return availableSpots;
    }

    public void setAvailableSpots(int availableSpots) {
        this.availableSpots = availableSpots;
    }

    public int getMaximumGuestNumber() {
        return maximumGuestNumber;
    }

    public void setMaximumGuestNumber(int maximumGuestNumber) {
        this.maximumGuestNumber = maximumGuestNumber;
    }

    public Restaurant() {
    }

    public Restaurant(int id, String name, String owner, String address, int availableSpots, int maximumGuestNumber, String imageUrl) {
        this.id = id;
        this.name = name;
        this.owner = owner;
        this.address = address;
        this.availableSpots = availableSpots;
        this.maximumGuestNumber = maximumGuestNumber;
        this.imageUrl = imageUrl;
    }
}
