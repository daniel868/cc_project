package org.auth.service;

import org.auth.model.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    void registerUser(User user);

    User findUserByUsername(String username);

}
