package com.campers.now.services.Impl;

import com.campers.now.models.Role;
import com.campers.now.models.User;
import com.campers.now.repositories.UserRepository;
import com.campers.now.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
@Transactional
public class UserServiceImpl implements UserService, UserDetailsService {
    UserRepository userRepository;
    PasswordEncoder passwordEncoder;

    @Override
    public List<User> getAll(Integer pageNumber, String property, Sort.Direction direction) {
        if (pageNumber == null)
            return userRepository.findAll();
        return userRepository.findAll(PageRequest.of((pageNumber <= 0 ? 1 : pageNumber) - 1, 10, Sort.by(List.of(Sort.Order.by(StringUtils.hasText(property) ? property : "id").with(direction)))))
                .stream().collect(Collectors.toUnmodifiableList());
    }

    @Override
    public User getById(Integer id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
    }

    @Override
    @Transactional
    public User add(User o) {
        try {
            o.setPassword(passwordEncoder.encode(o.getPassword()));
            return userRepository.save(o);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User update(User o) {
        var original = getById(o.getId());
        o.setPassword(original.getPassword());
        try {
            return userRepository.save(o);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByEmail(username);
        if (user.isPresent()) {
            return new org.springframework.security.core.userdetails.User(user.get().getEmail(), user.get().getPassword(), user.get().isActive()
                    , true, true,
                    true, getAuthorities(user.get().getRoles()));
        }
        throw new UsernameNotFoundException("User not found");
    }

    public Collection<? extends GrantedAuthority> getAuthorities(List<Role> roles) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getName().name()));
        }
        return authorities;
    }
}
