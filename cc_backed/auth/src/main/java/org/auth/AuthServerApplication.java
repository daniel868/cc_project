package org.auth;

import org.auth.model.User;
import org.auth.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class AuthServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthServerApplication.class, args);
    }

    @Bean
    CommandLineRunner runner(PasswordEncoder passwordEncoder,
                             UserRepository userRepository) {
        User user = new User();
        user.setEmail("test@mail.com");
        user.setFirstName("test");
        user.setUsername("test");
        user.setLastName("test");
        user.setPassword(passwordEncoder.encode("test"));
        return args -> {
            userRepository.save(user);
        };
    }
}