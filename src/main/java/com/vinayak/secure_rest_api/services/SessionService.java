package com.vinayak.secure_rest_api.services;

import com.vinayak.secure_rest_api.entities.SessionEntity;
import com.vinayak.secure_rest_api.entities.User;
import com.vinayak.secure_rest_api.repositories.SessionRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SessionService {

    private final SessionRepo  sessionRepo;
    private final int SESSION_LIMIT = 10;

    public void generateNewSession(User user, String refreshToken) {

        List<SessionEntity> userSession = sessionRepo.findByUser(user);

        if(userSession.size() == SESSION_LIMIT){
            userSession.sort(Comparator.comparing(SessionEntity::getLastUsedAt));

            SessionEntity leastRecentSession = userSession.getFirst();
            sessionRepo.delete(leastRecentSession);
        }

        SessionEntity newSession = SessionEntity.builder()
                .user(user)
                .refreshToken(refreshToken)
                .build();
        sessionRepo.save(newSession);
    }

    public void validSession(String refreshToken) {
        SessionEntity session = sessionRepo.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new SessionAuthenticationException("Sessoin not found for refresh token: "+refreshToken));

        session.setLastUsedAt(LocalDateTime.now());

        sessionRepo.save(session);
    }


}
