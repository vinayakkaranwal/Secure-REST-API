package com.vinayak.secure_rest_api.services;

import com.vinayak.secure_rest_api.entities.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Value;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Set;

@Service
public class JwtService {

    @Value("${jwt.secretKey}")
    private String jwtSecretKey;

    public SecretKey generateSecretKey(){

        return Keys.hmacShaKeyFor(jwtSecretKey.getBytes(StandardCharsets.UTF_8));
    }

    public String createAccessToken(User user){
        return Jwts.builder()
                .subject(user.getId().toString())
                .claim("email", user.getEmail())
                .claim("roles", user.getRoles().toString())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis()+ 10*60*1000))   //10mins
                .signWith(generateSecretKey())
                .compact();
    }

    public String createRefreshToken(User user){
        return Jwts.builder()
                .subject(user.getId().toString())
                .issuedAt( new Date())
                .expiration(new Date(System.currentTimeMillis()+60*60*24*30*1000L))  //30days
                .signWith(generateSecretKey())
                .compact();
    }

    public Long generateUserIdFromToken(String token){
        Claims claim = Jwts.parser()
                .verifyWith(generateSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return Long.valueOf(claim.getSubject());
    }

}
