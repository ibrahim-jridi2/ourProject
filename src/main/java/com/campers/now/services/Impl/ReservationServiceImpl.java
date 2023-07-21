package com.campers.now.services.Impl;

import com.campers.now.models.Reservation;
import com.campers.now.repositories.ReservationRepository;
import com.campers.now.repositories.UserRepository;
import com.campers.now.services.ReservationService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@AllArgsConstructor
@Service
public class ReservationServiceImpl implements ReservationService {
    ReservationRepository reservationRepository;
    UserRepository userRepository;
    @Autowired
    public ReservationServiceImpl(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

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
    // @Scheduled(cron = "0 */1 * * * *") // Runs every 1 minute
     @Scheduled(cron = "0 0 0 * * *") // Runs every day at midnight
    public void deactivateOldReservations() {
        LocalDate today = LocalDate.now();
        Date dateToday = java.sql.Date.valueOf(today);
        reservationRepository.deactivateOldReservations(dateToday);
    }

    public ResponseEntity<List<Object[]>> getReservationStatisticsByMonth() {
        List<Object[]> statistics = reservationRepository.getReservationCountByMonth();
        return ResponseEntity.ok(statistics);
    }
}
