package com.vinayak.secure_rest_api.services;

import com.vinayak.secure_rest_api.dto.*;
import com.vinayak.secure_rest_api.entities.User;
import com.vinayak.secure_rest_api.entities.enums.Roles;
import com.vinayak.secure_rest_api.repositories.UserRepositorie;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepositorie userRepo;
    private final ModelMapper  modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager  authenticationManager;
    private final JwtService jwtService;
    private final UserService userService;
    private final SessionService sessionService;

    @Transactional
    public UserDTO signUp(SignupDTO signupDTO) {

        Optional<User> user = userRepo.findByEmail(signupDTO.getEmail());

        if (user.isPresent()) throw new BadCredentialsException("Cannot signup, User already exists with email "+signupDTO.getEmail());

        User mapperUser = modelMapper.map(signupDTO, User.class);
        mapperUser.setPassword(passwordEncoder.encode(mapperUser.getPassword()));
        mapperUser.setRoles(Set.of(Roles.USER));
        mapperUser.setPermissions(new HashSet<>());

        User saveUser = userRepo.save(mapperUser);

        return modelMapper.map(saveUser, UserDTO.class);
    }

    public LoginResponseDTO login(LoginDTO  loginDTO) {

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword()));

        User user = (User) authentication.getPrincipal();

        String accessToken = jwtService.createAccessToken(user);
        String refreshToken = jwtService.createRefreshToken(user);
        sessionService.generateNewSession(user, refreshToken);

        return new LoginResponseDTO(user.getId(), accessToken, refreshToken);
    }

    public LoginResponseDTO refreshToken(String refreshToken) {
        Long userId = jwtService.generateUserIdFromToken(refreshToken);

        sessionService.validSession(refreshToken);

        User user = userService.getUserById(userId);

        String accessToken = jwtService.createAccessToken(user);

        return new LoginResponseDTO(user.getId(), accessToken, refreshToken);
    }
}
