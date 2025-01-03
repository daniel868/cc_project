package org.auth.controller;

import org.auth.model.SignupRequest;
import org.auth.model.User;
import org.auth.repository.UserRepository;
import org.service.customer.pojo.CustomerDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @RequestMapping(value = "/{customerId}", method = RequestMethod.POST)
    public ResponseEntity<Boolean> updateUser(@RequestBody CustomerDto customerDto,
                                              @PathVariable("customerId") Integer customerId) {
        User user = userRepository.findByCustomerId(customerId)
                .orElse(null);
        if (user == null) {
            throw new RuntimeException("Could not found user with id: " + customerId);
        }
        user.setUsername(customerDto.getName());
        user.setEmail(customerDto.getPhoneNumber());

        User saved = userRepository.save(user);
        return ResponseEntity.ok(true);
    }
}
