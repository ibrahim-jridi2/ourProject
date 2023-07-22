package com.campers.now.services.Impl;

import com.campers.now.DTO.UserRequest;
import com.campers.now.config.JwtService;
import com.campers.now.exceptions.BadRequestHttpException;
import com.campers.now.exceptions.NotFoundHttpException;
import com.campers.now.exceptions.UnAuthorizedHttpException;
import com.campers.now.models.Role;
import com.campers.now.models.User;
import com.campers.now.models.enums.RoleType;
import com.campers.now.repositories.UserRepository;
import com.campers.now.repositories.ViewRepository;
import com.campers.now.services.UserService;
import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
@Transactional
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {
    UserRepository userRepository;
    ViewRepository viewRepository;

    private static User buildUser(UserRequest o) {
        return User.builder()
                .id(o.getId())
                .nom(o.getNom())
                .prenom(o.getPrenom())
                .avatar(o.getAvatar())
                .email(o.getEmail())
                .isEmailValide(o.isEmailValide())
                .isActive(o.isActive())
                .tokenExpired(o.isTokenExpired())
                .password(o.getPassword())
                .roles(o.getRoles())
                .build();
    }

    @Override
    public List<User> getAll(Integer pageNumber, String property, Sort.Direction direction) {
        if (pageNumber == null)
            return userRepository.findAll();
        return userRepository.findAll(PageRequest.of((pageNumber <= 0 ? 1 : pageNumber) - 1, 10, Sort.by(List.of(Sort.Order.by(StringUtils.hasText(property) ? property : "id").with(direction)))))
                .stream().collect(Collectors.toUnmodifiableList());
    }

    @Override
    public User getById(Integer id) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        HttpServletRequest request = requestAttributes.getRequest();

        // Retrieve the token from the header
        String token = request.getHeader("Authorization").substring(7);
        JwtService jwtService = new JwtService();
        var idClaim = Integer.valueOf(jwtService.extractClaim(token, Claims::getId));
        log.debug(idClaim.toString());
        if (!idClaim.equals(id) && principal.getAuthorities().stream().noneMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(RoleType.ROLE_SUPER_ADMIN.name()))) {
            throw new UnAuthorizedHttpException("Unauthorized access");
        } else {
            return userRepository.findById(id)
                    .orElseThrow(() -> new NotFoundHttpException("User not found"));
        }
    }

    @Override
    @Transactional
    public User add(UserRequest o) {
        if (userRepository.findByEmail(o.getEmail()).isPresent()) {
            throw new BadRequestHttpException("Email unavailable");
        }
        try {
            o.setPassword(new BCryptPasswordEncoder().encode(o.getPassword()));
            o.setActive(true);
            o.setEmailValide(false);
            o.setTokenExpired(false);
            var user = buildUser(o);
            return userRepository.save(user);
        } catch (Exception e) {
            if (e instanceof IllegalArgumentException) throw new BadRequestHttpException("invalid request");
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getPasswordByEmail(String email) {
        return userRepository.getPasswordByEmail(email);
    }

    @Override
    public User update(UserRequest o) {
        var original = getById(o.getId());
        var userByEmail = userRepository.findByEmail(o.getEmail());
        if (userByEmail.isPresent() && !Objects.equals(userByEmail.get().getId(), original.getId())) {
            throw new BadRequestHttpException("Email unavailable");
        }
        o.setPassword(getPasswordByEmail(original.getEmail()));
        o.setId(original.getId());
        var user = buildUser(o);
        try {
            return userRepository.save(user);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional
    public User getByEmail(String email) {
        var user = userRepository.findByEmail(email).orElseThrow(
                () -> new BadRequestHttpException("Bad credentials"));
        var pass = userRepository.getPasswordByEmail(email);
        user.setPassword(pass);
        return user;
    }

    @Override
    public List<Map<String, Object>> getStatsByUserIdForEveryYearAndMonth(Integer userId) {
        return userRepository.getRevenueByUserIdForEveryYearAndMonth(userId);
    }

    @Override
    public List<Map<String, Object>> getStatsByUserIdAndSeason(Integer userId) {
        return userRepository.getRevenueByUserIdAndSeason(userId);
    }

    @Override
    public List<Map<String, Object>> getRecentlyViewedCampingCenters(Integer userID) {
        return viewRepository.getRecentlyViewedCampingCenters(userID);
    }

    @Override
    public List<Map<String, Object>> getRecentlyViewedPosts(Integer userID) {
        return viewRepository.getRecentlyViewedPosts(userID);
    }

    @Override
    public List<Map<String, Object>> getSuggestedPosts(List<String> tags) {
        return viewRepository.getSuggestedPosts(tags);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = getByEmail(username);
        if (user != null) {
            if (!user.isActive())
                throw new UnAuthorizedHttpException("Your account is no longer active, contact administrator");
            return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), true
                    , true, true,
                    true, getAuthorities(user.getRoles()));
        }
        throw new BadRequestHttpException("Bad credentials");
    }

    public Collection<? extends GrantedAuthority> getAuthorities(List<Role> roles) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getName().name()));
        }
        return authorities;
    }
}
