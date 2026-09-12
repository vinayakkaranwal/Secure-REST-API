package com.vinayak.secure_rest_api.config;

import com.vinayak.secure_rest_api.entities.enums.Permissions;
import com.vinayak.secure_rest_api.entities.enums.Roles;
import com.vinayak.secure_rest_api.filters.JWTAuthFilter;
import com.vinayak.secure_rest_api.handlers.OAuth2SuccessHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final JWTAuthFilter jwtAuthFilter;
    private final OAuth2SuccessHandler  oAuth2SuccessHandler;
    private static final String[] publicRoutes = {
            "/auth/**",
            "/error",
            "/home.html"
    };

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeHttpRequests(auth -> auth

                        .requestMatchers("/actuator/**").permitAll()

                        .requestMatchers(
                                "/v3/api-docs/**",
                                "/swagger-ui/**",
                                "/swagger-ui.html"
                        ).permitAll()

                        .requestMatchers(publicRoutes).permitAll()

                        .requestMatchers("/admin/**").hasRole(Roles.ADMIN.name())

                        .requestMatchers(HttpMethod.GET, "/task/**").hasAuthority(Permissions.TASK_VIEW.name())
                        .requestMatchers(HttpMethod.POST, "/task/**").hasAuthority(Permissions.TASK_CREATE.name())
                        .requestMatchers(HttpMethod.PUT, "/task/**").hasAuthority(Permissions.TASK_UPDATE.name())
                        .requestMatchers(HttpMethod.DELETE, "/task/**").hasAuthority(Permissions.TASK_DELETE.name())

                        .anyRequest().authenticated()
                )
                .csrf(crsf -> crsf.disable())
                .sessionManagement(sessionConfig -> sessionConfig
                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .formLogin(Customizer.withDefaults())
                .logout(Customizer.withDefaults())
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .oauth2Login(oath2Config -> oath2Config
                        .failureUrl("/login?error=true")
                        .successHandler(oAuth2SuccessHandler)
                );

        return httpSecurity.build();
    }

    @Bean
    AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
