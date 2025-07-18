package org.example.schoolmanagementsystem.repositories;

import org.example.schoolmanagementsystem.entities.ScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<ScheduleEntity, Long> {
    List<ScheduleEntity> findByClassNumber(Integer classNumber);

    boolean existsByClassNumberAndDayOfWeekAndStartTimeLessThanEqualAndEndTimeGreaterThanEqual(
            int classNumber, int dayOfWeek, LocalTime endTime, LocalTime startTime);
}
