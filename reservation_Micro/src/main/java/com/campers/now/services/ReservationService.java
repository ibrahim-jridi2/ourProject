package com.campers.now.services;

import com.campers.now.models.Reservation;


import java.util.List;

public interface ReservationService {
    List<Reservation> findAll();
    Reservation findById(Long id);
    Reservation save(Reservation reservation);
    void deleteById(Long id);
}
