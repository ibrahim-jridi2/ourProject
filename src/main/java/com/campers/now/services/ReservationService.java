package com.campers.now.services;

import com.campers.now.models.Reservation;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ReservationService {
    List<Reservation> getAll();

    Reservation getById(Integer id);

    Reservation add(Reservation o);

    Reservation update(Reservation o);
     int calculateTotalNumberOfDays(List<Reservation> reservations) ;
    ResponseEntity<List<Object[]>> getReservationStatisticsByMonth();
    }
