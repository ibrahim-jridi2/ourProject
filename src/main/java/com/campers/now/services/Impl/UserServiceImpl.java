package com.campers.now.services.Impl;

import com.campers.now.models.User;
import com.campers.now.repositories.UserRepository;
import com.campers.now.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    UserRepository userRepository;

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
            return userRepository.save(o);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User update(User o) {
        getById(o.getId());
        try {
            return userRepository.save(o);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
