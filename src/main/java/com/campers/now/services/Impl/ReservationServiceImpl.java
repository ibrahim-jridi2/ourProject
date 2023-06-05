package com.campers.now.services.Impl;

import com.campers.now.models.Reservation;
import com.campers.now.repositories.ReservationRepository;
import com.campers.now.services.ReservationService;
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
public class ReservationServiceImpl implements ReservationService {
    ReservationRepository reservationRepository;

    @Override
    public List<Reservation> getAll(Integer pageNumber, String property, Sort.Direction direction) {
        if (pageNumber == null)
            return reservationRepository.findAll();
        return reservationRepository.findAll(PageRequest.of((pageNumber <= 0 ? 1 : pageNumber) - 1, 10, Sort.by(List.of(Sort.Order.by(StringUtils.hasText(property) ? property : "id").with(direction)))))
                .stream().collect(Collectors.toUnmodifiableList());
    }

    @Override
    public Reservation getById(Integer id) {
        return reservationRepository.findById(id)
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
    }

    @Override
    @Transactional
    public Reservation add(Reservation o) {
        try {
            return reservationRepository.save(o);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Reservation update(Reservation o) {
        getById(o.getId());
        try {
            return reservationRepository.save(o);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
