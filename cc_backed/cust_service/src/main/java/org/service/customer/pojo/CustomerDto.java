package org.service.customer.pojo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CustomerDto {
    private int id;
    private String name;
    private String phoneNumber;
    private String emailAddress;
}
