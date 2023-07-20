package com.campers.now.services.Impl;

import com.campers.now.models.Reservation;
import com.campers.now.models.User;
import com.campers.now.repositories.ReservationRepository;
import com.campers.now.repositories.UserRepository;
import com.campers.now.services.ReservationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Date;
import java.util.List;

import java.util.concurrent.TimeUnit;

@AllArgsConstructor
@Service
public class ReservationServiceImpl implements ReservationService {
    ReservationRepository reservationRepository;
    UserRepository userRepository;

    @Override
    public List<Reservation> getAll() {
            return reservationRepository.findAll();
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
    @Override
    public int calculateTotalNumberOfDays(List<Reservation> reservations) {
        int totalDays = 0;

        for (Reservation reservation : reservations) {
            if (reservation.isActive()) {
                Date startDate = reservation.getDateStart();
                Date endDate = reservation.getDateEnd();

                long differenceInMillis = endDate.getTime() - startDate.getTime();
                int days = (int) TimeUnit.DAYS.convert(differenceInMillis, TimeUnit.MILLISECONDS);

                totalDays += days;
            }
        }

        return totalDays;
    }




}
