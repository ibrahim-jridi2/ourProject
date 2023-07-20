package com.campers.now.repositories;

import com.campers.now.models.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
    @Query(value = "select * from reservation re where res.user_id=:userId",nativeQuery = true)
    List<Reservation> findReservationByUserId(Integer userId);

}
