package com.campers.now.services.Impl;

import com.campers.now.auth.AuthenticationResponse;
import com.campers.now.auth.LoginRequest;
import com.campers.now.auth.RegisterRequest;
import com.campers.now.config.JwtService;
import com.campers.now.models.Role;
import com.campers.now.models.User;
import com.campers.now.models.enums.RoleType;
import com.campers.now.repositories.RoleRepository;
import com.campers.now.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

@AllArgsConstructor
@Service
@Transactional
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final RoleRepository roleRepository;


    public AuthenticationResponse authenticate(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = userRepository.findByEmail(request.getEmail()).orElseThrow(
                ()->new HttpClientErrorException(HttpStatus.NOT_FOUND));
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken).build();
    }

    public AuthenticationResponse register(RegisterRequest request) {
        Role role = roleRepository.findByName(RoleType.ROLE_CAMPER);
        var user = User.builder()
                .nom(request.getNom())
                .prenom(request.getPrenom())
                .email(request.getEmail())
                .isEmailValide(false)
                .isActive(true)
                .tokenExpired(false)
                .password(passwordEncoder.encode(request.getPassword()))
                .roles(List.of(role))
                .build();
        try {
            userRepository.save(user);
            var jwtToken = jwtService.generateToken(user);
            return AuthenticationResponse.builder()
                    .token(jwtToken).build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
