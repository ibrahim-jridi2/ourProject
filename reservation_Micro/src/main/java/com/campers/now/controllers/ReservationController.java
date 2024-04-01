package com.campers.now.controllers;

import com.campers.now.models.Reservation;
import com.campers.now.services.ReservationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Reservation Management")
@RestController
@AllArgsConstructor
@RequestMapping("reservations")
@CrossOrigin(origins = "http://localhost:4200")
public class ReservationController {
    private final ReservationService reservationService;

    @PostMapping
    @PreAuthorize("hasRole('ROLE_CAMPER')")
    public Reservation add(@RequestBody Reservation reservation) {
        return reservationService.add(reservation);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_CAMPER')")
    public Reservation update(@RequestBody Reservation reservation, @PathVariable("id") Integer id) {
        reservation.setId(id);
        return reservationService.update(reservation);
    }

    @GetMapping("/{id}")
    public Reservation getOne(@PathVariable("id") Integer id) {
        return reservationService.getById(id);
    }

    @GetMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<Reservation> getAll() {

        return reservationService.getAll();
    }
    @GetMapping("/statistics")
    public ResponseEntity<List<Object[]>> getReservationStatisticsByMonth() {

        return reservationService.getReservationStatisticsByMonth();
    }
}
