package com.vinayak.secure_rest_api.repositories;

import com.vinayak.secure_rest_api.entities.SessionEntity;
import com.vinayak.secure_rest_api.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SessionRepo extends JpaRepository<SessionEntity, Long> {
    List<SessionEntity> findByUser(User  user);

    Optional<SessionEntity> findByRefreshToken(String refreshToken);
}
