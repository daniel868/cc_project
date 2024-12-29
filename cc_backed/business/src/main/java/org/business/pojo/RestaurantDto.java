package org.business.pojo;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantDto {
    private int id;
    private String name;
    private String address;
    private int availableSpots;
    private String imageUrl;
    private String description;

}
