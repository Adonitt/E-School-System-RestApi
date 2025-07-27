package org.example.schoolmanagementsystem.services.impls;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.schoolmanagementsystem.entities.GradeEntity;
import org.example.schoolmanagementsystem.entities.administration.StudentEntity;
import org.example.schoolmanagementsystem.enums.SemesterEnum;
import org.example.schoolmanagementsystem.repositories.GradeRepository;
import org.example.schoolmanagementsystem.repositories.StudentRepository;
import org.example.schoolmanagementsystem.services.interfaces.SemesterUpdateService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SemesterUpdateServiceImpl implements SemesterUpdateService {

    private final StudentRepository studentRepository;
    private final GradeRepository gradeRepository;

    @Transactional
    public void updateCurrentSemesterForAllStudents() {
        List<StudentEntity> students = studentRepository.findAll();
        Month currentMonth = LocalDate.now().getMonth();

        for (StudentEntity student : students) {
            SemesterEnum currentSemester = student.getCurrentSemester();

            SemesterEnum nextSemester = getNextSemester(currentSemester, currentMonth);
            if (nextSemester != null) {
                student.setCurrentSemester(nextSemester);
            } else {
                student.setGraduated(true);
                calculateGpaForStudent(student.getId());
                student.setActive(false);
                studentRepository.save(student);
            }
        }
        studentRepository.saveAll(students);
    }

    private SemesterEnum getNextSemester(SemesterEnum current, Month month) {
        return switch (current) {
            case SEMESTER_1 -> (month == Month.FEBRUARY) ? SemesterEnum.SEMESTER_2 : null;
            case SEMESTER_2 -> (month == Month.JUNE) ? SemesterEnum.SEMESTER_3 : null;
            case SEMESTER_3 -> (month == Month.OCTOBER) ? SemesterEnum.SEMESTER_4 : null;
            case SEMESTER_4 -> (month == Month.FEBRUARY) ? SemesterEnum.SEMESTER_5 : null;
            case SEMESTER_5 -> (month == Month.JUNE) ? SemesterEnum.SEMESTER_6 : null;
            case SEMESTER_6 -> null;
        };
    }

    @Override
    public void updateAcademicYearForAllStudents() {
        LocalDate today = LocalDate.now();

        if (today.getMonth() == Month.JANUARY) {
            int currentYear = today.getYear();
            String newAcademicYear = currentYear + "/" + (currentYear + 1);

            List<StudentEntity> allStudents = studentRepository.findAll();
            for (StudentEntity student : allStudents) {
                student.setAcademicYear(newAcademicYear);
            }

            studentRepository.saveAll(allStudents);

            System.out.println("Academic year updated to: " + newAcademicYear);
        }
    }

    @Override
    public double calculateGpaForStudent(Long studentId) {
        List<GradeEntity> grades = gradeRepository.findAllByStudentId(studentId);

        if (grades.isEmpty()) {
            return 0.0;
        }

        int total = 0;
        for (GradeEntity grade : grades) {
            total += grade.getGrade().getValue();
        }

        return (double) total / grades.size();
    }


}
