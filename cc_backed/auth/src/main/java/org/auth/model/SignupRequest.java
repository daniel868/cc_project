package org.auth.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SignupRequest implements Serializable {
    private String username;
    private String password;
    private String firstname;
    private String lastname;
    private String email;
    private String type;
    private String phoneNumber;

}