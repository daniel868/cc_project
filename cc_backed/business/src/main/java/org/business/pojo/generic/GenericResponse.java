package org.business.pojo.generic;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GenericResponse<T> {
    private boolean success;
    private T payload;
    private Integer statusCode;
    private String errorMessage;
}
