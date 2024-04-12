package com.example.AppUser.services;

import com.example.AppUser.dtos.EntityMapper;
import com.example.AppUser.dtos.UserDto;
import com.example.AppUser.models.ApplicationUser;
import com.example.AppUser.models.Role;
import com.example.AppUser.repository.RoleRepository;
import com.example.AppUser.utils.EmailUtil;
import jakarta.mail.MessagingException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import com.example.AppUser.repository.UserRepository;

import java.util.*;

@Service
@AllArgsConstructor
public class UserService implements UserDetailsService {

    private  EntityMapper<ApplicationUser,UserDto> mapper;
    private PasswordEncoder passwordEncoder;
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private EmailUtil emailUtil;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        System.out.println("In the user details service");

        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("user is not valid"));
    }

    public UserDto getUserByToken() {
        JwtAuthenticationToken jwtAuthenticationToken = (JwtAuthenticationToken) SecurityContextHolder
                .getContext()
                .getAuthentication();
        Jwt jwt = jwtAuthenticationToken.getToken();
        String username = jwt.getClaimAsString("sub");
        Optional<ApplicationUser> user = userRepository.findByUsername(username);
        ApplicationUser applicationUser = user.orElseThrow(() -> new UsernameNotFoundException("User not found for username: " + username));
        return mapper.fromBasic(applicationUser, UserDto.class);
    }

    public void userToAdmin(String  username) throws UsernameNotFoundException {
        ApplicationUser user = userRepository.findByUsername(username).orElse(null);

        if (user != null) {
            Optional<Role> managerRole = roleRepository.findByAuthority("ADMIN");

            if (managerRole.isPresent()) {
                Set<Role> authorities = new HashSet<>();
                authorities.add(managerRole.get());

                user.setAuthorities(authorities);
                userRepository.save(user);
            } else {
                throw new RuntimeException("Admin role not found.");
            }
        } else {
            throw new UsernameNotFoundException("User not found.");
        }
    }
    public List<UserDto> getAllUsersByRoles(String s) {
        List<ApplicationUser> usersWithUserRole = userRepository.findByAuthorities_Authority(s);

        List<UserDto> userDtos = new ArrayList<>();
        for (ApplicationUser user : usersWithUserRole) {
            userDtos.add(mapper.fromBasic(user, UserDto.class));
        }

        return userDtos;
    }
    public void removeMyaccount() {
        UserDto userDto = getUserByToken();
        Optional<ApplicationUser> existingUserOptional = userRepository.findByUsername(userDto.getUsername());

        existingUserOptional.ifPresentOrElse(
                existingUser -> userRepository.delete(existingUser),
                () -> {
                    throw new EntityNotFoundException("User not found for username: " + userDto.getUsername());
                }
        );
    }
    public void update(UserDto dto) {
        ApplicationUser user2 = userRepository.getByUsername(dto.getUsername());
        ApplicationUser user = mapper.fromDTO(dto,ApplicationUser.class);
        user.setUserId(user.getUserId());
        userRepository.save(user);
    }


    @Transactional
    public UserDto update2(UserDto dto) {
        ApplicationUser userupdated = mapper.fromDTO(dto, ApplicationUser.class);
        userupdated.setId(getUserByToken().getUserId());
        userRepository.save(userupdated);
        return dto;
    }

    public void forgetpassword(String email) throws MessagingException {
    emailUtil.sendSetPasswordEmail(email);
    }
    public void setpassword(String email , String password){
        ApplicationUser user= userRepository.getByEmail(email);
        String encodedPassword = passwordEncoder.encode(password);
        user.setPassword(encodedPassword);
        userRepository.save(user);
        System.out.println( "new password Set successfully Login with the new password");
    }

    public ApplicationUser getByUserId(Integer id) {
        ApplicationUser user=userRepository.getById(id);
        return user;
    }

    public ApplicationUser getUserByUsername(String username) {
        return userRepository.getByUsername(username);
    }
}
