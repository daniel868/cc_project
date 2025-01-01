package org.auth.service;

import org.auth.model.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    User saveUser(User user);

    User findUserByUsername(String username);

}
