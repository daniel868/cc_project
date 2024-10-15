package org.auth.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupResponse {
    private boolean success;
    private String message;
    private Object data;

    public SignupResponse(boolean success, String message, Object data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }
}
