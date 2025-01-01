package org.auth;

import org.auth.model.Role;
import org.auth.model.RoleEnum;
import org.auth.model.User;
import org.auth.repository.RoleRepository;
import org.auth.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@SpringBootApplication
public class AuthServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthServerApplication.class, args);
    }

    @Bean
    CommandLineRunner runner(PasswordEncoder passwordEncoder,
                             UserRepository userRepository,
                             RoleRepository roleRepository) {
        Role adminRole = new Role();
        adminRole.setRole(RoleEnum.ADMIN);
        Role userRole = new Role();
        userRole.setRole(RoleEnum.USER);

        roleRepository.saveAll(List.of(adminRole, userRole));

        Role roleByRoleAdmin = roleRepository.findRoleByRole(RoleEnum.ADMIN);

        User user = new User();
        user.setEmail("test@mail.com");
        user.setFirstName("test");
        user.setUsername("test");
        user.setLastName("test");
        user.setPassword(passwordEncoder.encode("test"));
        user.addRole(roleByRoleAdmin);
        user.setCustomerId(1);

        return args -> {
            userRepository.save(user);
        };
    }
}