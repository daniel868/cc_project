package org.business.pojo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RestaurantDto {
    private int id;
    private String name;
    private String address;
    private int availableSpots;
    private String imageUrl;

}
