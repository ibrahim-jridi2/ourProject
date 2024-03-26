package com.campers.now.repositories;

import com.campers.now.models.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
    @Query(value = "select * from reservation re where res.user_id=:userId",nativeQuery = true)
    List<Reservation> findReservationByUserId(Integer userId);

    @Transactional
    @Modifying
    @Query("UPDATE Reservation r SET r.isActive = false WHERE r.dateEnd < :today AND r.isActive = true")
    void deactivateOldReservations(Date today);

    @Query(value = "SELECT MONTH(dateStart) as month, COUNT(*) as count FROM Reservation GROUP BY MONTH(dateStart)")
    List<Object[]> getReservationCountByMonth();
}
