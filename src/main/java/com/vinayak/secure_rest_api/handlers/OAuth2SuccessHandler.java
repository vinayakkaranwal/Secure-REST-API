package com.vinayak.secure_rest_api.handlers;

import com.vinayak.secure_rest_api.entities.User;
import com.vinayak.secure_rest_api.entities.enums.Roles;
import com.vinayak.secure_rest_api.services.JwtService;
import com.vinayak.secure_rest_api.services.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Set;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    public final UserService userService;
    public final JwtService jwtService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) authentication;
        DefaultOAuth2User defaultOAuth2User = (DefaultOAuth2User) token.getPrincipal();
        String email = defaultOAuth2User.getAttribute("email");

        User user = userService.getUserByEmail(email);

        if(user == null){
            User newUser = User.builder()
                    .username(defaultOAuth2User.getAttribute("name"))
                    .email(email)
                    .password(UUID.randomUUID().toString())
                    .roles(Set.of(Roles.USER))
                    .build();

            user = userService.save(newUser);
        }

        String accessToken = jwtService.createAccessToken(user);
        String refreshToken = jwtService.createRefreshToken(user);

        Cookie cookie = new Cookie("refreshToken", refreshToken);
        cookie.setHttpOnly(true);

        response.addCookie(cookie);

        String frontendUrl = "http://localhost:8080/home.html?token="+accessToken;

        response.sendRedirect(frontendUrl);

    }
}
