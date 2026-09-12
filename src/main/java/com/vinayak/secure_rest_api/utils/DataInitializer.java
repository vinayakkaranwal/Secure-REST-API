package com.vinayak.secure_rest_api.utils;

import com.vinayak.secure_rest_api.entities.User;
import com.vinayak.secure_rest_api.entities.enums.Roles;
import com.vinayak.secure_rest_api.repositories.UserRepositorie;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    public final UserRepositorie userRepo;
    public final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if(userRepo.findByUsername("admin") == null ) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setEmail("admin@gmail.com");
            admin.setPassword(passwordEncoder.encode("87914618"));
            admin.setRoles(Set.of(Roles.ADMIN));

            userRepo.save(admin);
        }
    }
}
