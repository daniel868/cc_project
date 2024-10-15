package org.auth.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
public class AuthRequest implements Serializable {
    @Getter
    @Setter
    private String username;
    @Getter
    @Setter
    private String password;
}
