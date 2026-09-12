package com.vinayak.secure_rest_api.repositories;

import com.vinayak.secure_rest_api.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepositorie extends JpaRepository<User, Long> {
    User findByUsername(String username);

    Optional<User> findByEmail(String email);

    Optional<User> findById(Long id);

    User getUserById(Long id);

    void deleteByUsername(String username);

    boolean existsUserByUsername(String username);

}
