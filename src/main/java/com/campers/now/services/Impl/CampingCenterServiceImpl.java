package com.campers.now.services.Impl;

import com.campers.now.models.CampingCenter;
import com.campers.now.models.Reservation;
import com.campers.now.repositories.ActivityRepository;
import com.campers.now.repositories.CampingCenterRepository;
import com.campers.now.services.CampingCenterService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@AllArgsConstructor
@Slf4j
@Service
public class CampingCenterServiceImpl implements CampingCenterService {
    CampingCenterRepository campingCenterRepository;
    ActivityRepository activityRepository;
    ReservationServiceImpl reserv;

    @Override
    public List<CampingCenter> getAll() {

            return campingCenterRepository.findAll();

    }

    @Override
    public CampingCenter getById(Integer id) {
        return campingCenterRepository.findById(id)
                .orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
    }

    @Override
    @Transactional
    public CampingCenter add(CampingCenter o) {
        try {
            CampingCenter campingCenter = campingCenterRepository.save(o);
            if (campingCenter.getActivities() != null && !campingCenter.getActivities().isEmpty())
                campingCenter.getActivities().forEach(activity -> activity.setCampingCenter(campingCenter));
            return campingCenter;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public CampingCenter update(CampingCenter o) {
        try {
            getById(o.getId());
            return campingCenterRepository.save(o);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Integer id) {
        getById(id);
        try {
            campingCenterRepository.deleteById(id);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public CampingCenter addActivitybyCampingcenterId(Integer campingcenterId, Integer activityId) {
        CampingCenter campingCenter = campingCenterRepository.findById(campingcenterId).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND));
        campingCenter.getActivities().add(activityRepository.findById(activityId).orElseThrow(() -> new HttpClientErrorException(HttpStatus.NOT_FOUND)));
        return campingCenterRepository.save(campingCenter);
    }
    public double[] calculateOccupancyRate() {
        int totalCapacity = 0;
        int occupiedSpaces = 0;
        List<CampingCenter> campingCenters = campingCenterRepository.findAll();

        for (CampingCenter campingCenter : campingCenters) {
            List<Reservation> reservations = campingCenter.getReservations();
            totalCapacity += campingCenter.getCapacity();
            occupiedSpaces += reservations.stream()
                    .filter(Reservation::isActive)
                    .mapToInt(Reservation::getNumberReserved)
                    .sum();
        }

        double occupancyRate = (double) occupiedSpaces / totalCapacity * 100;
        double occupancyRatePercent = Math.round(occupancyRate * 100.0) / 100.0;
        double unoccupiedSpacesPercent = Math.round((100 - occupancyRate) * 100.0) / 100.0;

        return new double[] { unoccupiedSpacesPercent, occupancyRatePercent };
    }

    //Average  Rate
 @Override
 public double[] calculateADR() {
     List<CampingCenter> campingCenters = campingCenterRepository.findAll();
     double[] adrValues = new double[12]; // Assuming there are 12 months

     for (CampingCenter campingCenter : campingCenters) {
         List<Reservation> reservations = campingCenter.getReservations();

         int[] totalOccupiedUnitsByMonth = new int[12];
         int[] totalNumberOfDaysByMonth = new int[12];
         double[] totalRevenueByMonth = new double[12];

         for (Reservation reservation : reservations) {
             if (reservation.isActive()) {
                 Date startDate = reservation.getDateStart();
                 Date endDate = reservation.getDateEnd();
                 int numOfDays = calculateNumberOfDays(startDate, endDate);

                 Calendar startCalendar = Calendar.getInstance();
                 startCalendar.setTime(startDate);

                 Calendar endCalendar = Calendar.getInstance();
                 endCalendar.setTime(endDate);

                 while (startCalendar.before(endCalendar) || startCalendar.equals(endCalendar)) {
                     int monthIndex = startCalendar.get(Calendar.MONTH);
                     totalOccupiedUnitsByMonth[monthIndex] += reservation.getNumberReserved();
                     totalNumberOfDaysByMonth[monthIndex]++;
                     totalRevenueByMonth[monthIndex] += reservation.getTotalAmount();

                     startCalendar.add(Calendar.DAY_OF_MONTH, 1);
                 }
             }
         }

         for (int i = 0; i < 12; i++) {
             if (totalNumberOfDaysByMonth[i] != 0 && totalOccupiedUnitsByMonth[i] != 0) {
                 double adr = totalRevenueByMonth[i] / totalOccupiedUnitsByMonth[i] / totalNumberOfDaysByMonth[i];
                 adrValues[i] = adr;
             }
         }
     }

     return adrValues;
 }

    private int calculateNumberOfDays(Date startDate, Date endDate) {
        long startMillis = startDate.getTime();
        long endMillis = endDate.getTime();
        long diffMillis = endMillis - startMillis;
        return (int) TimeUnit.DAYS.convert(diffMillis, TimeUnit.MILLISECONDS) + 1;
    }
    @Override
    public double calculateRevenuePerOccupiedSpace() {
        List<Reservation> reservations = reserv.getAll();
        double totalRevenue = 0;
        int totalOccupiedSpaces = 0;

        for (Reservation reservation : reservations) {
            if (reservation.isActive() && reservation.getDateEnd().after(new Date())) {

                totalRevenue += reservation.getTotalAmount();
                int campingPeriodInDays = calculateNumberOfDays(reservation.getDateStart(), reservation.getDateEnd());
                totalOccupiedSpaces += reservation.getNumberReserved() * campingPeriodInDays;



            }
        }

        double revenuePerOccupiedSpace = totalRevenue / totalOccupiedSpaces;
        return revenuePerOccupiedSpace;
    }


}
