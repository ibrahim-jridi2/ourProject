package com.campers.now.services.Impl;

import com.campers.now.models.User;
import com.campers.now.DTO.AuthenticationResponse;
import com.campers.now.DTO.LoginRequest;
import com.campers.now.DTO.RegisterRequest;
import com.campers.now.config.JwtService;
import com.campers.now.exceptions.BadRequestHttpException;
import com.campers.now.models.Role;
import com.campers.now.DTO.UserRequest;
import com.campers.now.models.enums.RoleType;
import com.campers.now.repositories.RoleRepository;
import com.campers.now.services.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;

@AllArgsConstructor
@Service
@Transactional
@Slf4j
public class AuthenticationService {
    private final UserService userService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final RoleRepository roleRepository;


    public AuthenticationResponse authenticate(LoginRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
        } catch (AuthenticationException e) {
            throw new BadRequestHttpException(e.getMessage());
        }
        var user = userService.getByEmail(request.getEmail());
        HashMap<String, Object> credentials = initializeCreds(user);
        var jwtToken = jwtService.generateToken(credentials, user);
        return AuthenticationResponse.builder()
                .token(jwtToken).user(user).build();
    }

    private static HashMap<String, Object> initializeCreds(User user) {
        var credentials = new HashMap<String, Object>();
        credentials.put("jti", user.getId());
        credentials.put("roles", user.getRoles());
        return credentials;
    }

    public AuthenticationResponse register(RegisterRequest request) {
        Role role = roleRepository.findByName(RoleType.ROLE_CAMPER);
        UserRequest user = UserRequest.builder()
                .nom(request.getNom())
                .prenom(request.getPrenom())
                .email(request.getEmail())
                .isEmailValide(false)
                .isActive(true)
                .tokenExpired(false)
                .password(request.getPassword())
                .roles(List.of(role))
                .build();
        try {
            var addedUser = userService.add(user);
            var credentials = initializeCreds(addedUser);
            var jwtToken = jwtService.generateToken(credentials, addedUser);
            return AuthenticationResponse.builder()
                    .token(jwtToken).user(addedUser).build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
