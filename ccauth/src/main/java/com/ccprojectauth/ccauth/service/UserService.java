package com.ccprojectauth.ccauth.service;

import com.ccprojectauth.ccauth.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public interface UserService {
    public void registerUser(User user);

    public User findUserByUsername(String username);

}
