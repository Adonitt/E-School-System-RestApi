package org.example.schoolmanagementsystem.configs;

import lombok.RequiredArgsConstructor;
import org.example.schoolmanagementsystem.services.interfaces.SemesterUpdateService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SemesterScheduler {

    private final SemesterUpdateService semesterUpdateService;

//    @Scheduled(cron = "*/15 * * * * ?")
        @Scheduled(cron = "0 0 0 1 * ?") // qdo muaj, ora 00:00 në ditën e parë
    public void runSemesterUpdate() {
        semesterUpdateService.updateCurrentSemesterForAllStudents();
        semesterUpdateService.updateAcademicYearForAllStudents();
    }
}
