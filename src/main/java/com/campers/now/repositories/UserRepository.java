package com.campers.now.repositories;

import com.campers.now.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    int countByEmail(String email);

    Optional<User> findByEmail(String username);

    @Query(value = "select password from user where email = :email",nativeQuery = true)
    String getPasswordByEmail(@Param("email") String username);


    @Query("SELECT " +
            "YEAR(r.dateStart) AS year, " +
            "MONTH(r.dateStart) AS month, " +
            "r.dateStart AS date, " +
            "SUM(r.totalAmount) AS revenue," +
            "COUNT(r.id) AS countReservation" +
            " FROM Reservation r " +
            "WHERE r.isConfirmed = true AND r.user.id = :userId " +
            "GROUP BY YEAR(r.dateStart), MONTH(r.dateStart)")
    List<Map<String, Object>> getRevenueByUserIdForEveryYearAndMonth(Integer userId);

    @Query("SELECT " +
            "a.season AS season, " +
            "SUM(r.totalAmount) AS revenue," +
            "COUNT(r.id) AS countReservation" +
            " FROM Reservation r " +
            "JOIN r.activities a " +
            "WHERE r.isConfirmed = true AND r.user.id = :userId " +
            "GROUP BY a.season")
    List<Map<String, Object>> getRevenueByUserIdAndSeason(Integer userId);
}
