package org.example.schoolmanagementsystem.services.impls;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.schoolmanagementsystem.dtos.grade.CRDGradeDto;
import org.example.schoolmanagementsystem.dtos.grade.UpdateGradeDto;
import org.example.schoolmanagementsystem.entities.AttendanceEntity;
import org.example.schoolmanagementsystem.entities.GradeEntity;
import org.example.schoolmanagementsystem.entities.SubjectEntity;
import org.example.schoolmanagementsystem.entities.administration.StudentEntity;
import org.example.schoolmanagementsystem.entities.administration.TeacherEntity;
import org.example.schoolmanagementsystem.enums.SemesterEnum;
import org.example.schoolmanagementsystem.exceptions.NotTheRightTeacherException;
import org.example.schoolmanagementsystem.exceptions.StudentCannotBeNotedException;
import org.example.schoolmanagementsystem.exceptions.StudentHasAlreadyANote;
import org.example.schoolmanagementsystem.mappers.GradeMapper;
import org.example.schoolmanagementsystem.repositories.*;
import org.example.schoolmanagementsystem.services.interfaces.EmailService;
import org.example.schoolmanagementsystem.services.interfaces.GradeService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class GradeServiceImpl implements GradeService {
    private final GradeRepository gradeRepository;
    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;
    private final SubjectRepository subjectRepository;
    private final GradeMapper gradeMapper;
    private final EmailService emailService;
    private final AttendanceRepository attendanceRepository;

    @Override
    public CRDGradeDto add(CRDGradeDto dto) {
        StudentEntity student = studentRepository.findById(dto.getStudentId())
                .orElseThrow(() -> new EntityNotFoundException("Student not found"));

        SubjectEntity subject = subjectRepository.findById(dto.getSubjectId())
                .orElseThrow(() -> new EntityNotFoundException("Subject not found"));

        String teacherEmail = AuthServiceImpl.getLoggedInUserEmail();

        TeacherEntity teacher = teacherRepository.findByEmail(teacherEmail)
                .orElseThrow(() -> new EntityNotFoundException("Logged-in teacher not found"));

        if (!teacher.getSubjects().contains(subject)) {
            throw new NotTheRightTeacherException("Teacher does not teach this subject.");
        }

        boolean exists = gradeRepository.existsByStudentAndSubjectAndTeacher(student, subject, teacher);
        if (exists) {
            throw new StudentHasAlreadyANote("This student already has a grade for this subject from this teacher.");
        }

        List<AttendanceEntity> attendances = attendanceRepository.findByStudentAndSubject(student, subject);
        long totalAttendances = attendances.size();
        long presentDays = attendances.stream().filter(AttendanceEntity::isPresent).count();
        double calculatedAttendance = totalAttendances == 0 ? 0 : (double) presentDays / totalAttendances;

        double usedAttendance = dto.getAttendancePercentageUsed() != null
                ? dto.getAttendancePercentageUsed() / 100.0
                : calculatedAttendance;

        if (usedAttendance < 0.6) {
            emailService.sendReexaminationNotification(student, subject, student.getAcademicYear(), student.getCurrentSemester(), calculatedAttendance);
            throw new StudentCannotBeNotedException("Student cannot be noted with less than 60% attendance! He will be given a reexamination!");
        }

        GradeEntity grade = new GradeEntity();
        grade.setSemester(student.getCurrentSemester());
        grade.setAcademicYear(student.getAcademicYear());

        grade.setTeacher(teacher);
        grade.setStudent(student);
        grade.setSubject(subject);
        grade.setGrade(dto.getGrade());
        grade.setDateGiven(LocalDate.now());

        grade.setAttendancePercentageUsed(usedAttendance);

        var savedGrade = gradeRepository.save(grade);

//        if (dto.getGrade().getValue() == 5) {
//            emailService.sendReexaminationNotificationDueToGrade(student, subject, student.getAcademicYear(), student.getCurrentSemester(), grade.getGrade());
//        } else {
//            emailService.sendGradeNotification(student, savedGrade);
//        }

        return gradeMapper.toDto(savedGrade);
    }


    @Override
    public List<CRDGradeDto> findAll() {
        return gradeRepository.findAll().stream()
                .map(gradeMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public CRDGradeDto findById(Long id) {
        return gradeMapper.toDto(gradeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Grade not found")));
    }

    @Override
    public UpdateGradeDto modify(Long id, UpdateGradeDto dto) {
        GradeEntity grade = gradeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Grade not found"));

        double usedAttendance = dto.getAttendancePercentageUsed() != null
                ? dto.getAttendancePercentageUsed() / 100.0
                : (grade.getAttendancePercentageUsed() != null ? grade.getAttendancePercentageUsed() : 0);

        if (usedAttendance < 0.6) {
            emailService.sendReexaminationNotification(grade.getStudent(), grade.getSubject(), grade.getAcademicYear(), grade.getSemester(), grade.getAttendancePercentageUsed());
            throw new StudentCannotBeNotedException("Student cannot be noted with less than 60% attendance! He will be given a reexamination!");
        }

        grade.setGrade(dto.getGrade());
        grade.setAcademicYear(dto.getAcademicYear());
        grade.setAttendancePercentageUsed(usedAttendance);

        var savedGrade = gradeRepository.save(grade);
//        emailService.sendGradeUpdateNotification(grade.getStudent(), savedGrade);
        return gradeMapper.toUpdateDto(savedGrade);
    }

    @Override
    public void removeById(Long id) {
        gradeRepository.deleteById(id);
    }

    @Override
    public Map<String, List<CRDGradeDto>> groupBySubject() {
        return gradeRepository.findAll().stream()
                .map(gradeMapper::toDto)
                .collect(Collectors.groupingBy(dto -> {
                    SubjectEntity subject = subjectRepository.findById(dto.getSubjectId()).orElse(null);
                    return subject != null ? subject.getName() : "Unknown";
                }));
    }

    @Override
    public Map<String, List<CRDGradeDto>> groupByAcademicYear() {
        return gradeRepository.findAll().stream()
                .map(gradeMapper::toDto)
                .collect(Collectors.groupingBy(CRDGradeDto::getAcademicYear));
    }

    @Override
    public Map<SemesterEnum, List<CRDGradeDto>> groupBySemester() {
        return gradeRepository.findAll().stream()
                .map(gradeMapper::toDto)
                .collect(Collectors.groupingBy(CRDGradeDto::getSemester));
    }

    @Override
    public Map<String, List<CRDGradeDto>> groupByStudent() {
        return gradeRepository.findAll().stream()
                .map(gradeMapper::toDto)
                .collect(Collectors.groupingBy(dto -> "Student #" + dto.getStudentId()));
    }

    @Override
    public Map<String, List<CRDGradeDto>> groupByTeacher() {
        return gradeRepository.findAll().stream()
                .collect(Collectors.groupingBy(
                        grade -> grade.getTeacher().getEmail(),  // ose grade.getTeacher().getId().toString()
                        Collectors.mapping(gradeMapper::toDto, Collectors.toList())
                ));
    }


}
