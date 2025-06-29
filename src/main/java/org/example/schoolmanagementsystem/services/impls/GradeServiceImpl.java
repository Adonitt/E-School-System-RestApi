package org.example.schoolmanagementsystem.services.impls;

import lombok.RequiredArgsConstructor;
import org.example.schoolmanagementsystem.dtos.grade.GradeRequestDto;
import org.example.schoolmanagementsystem.entities.GradeEntity;
import org.example.schoolmanagementsystem.entities.SubjectEntity;
import org.example.schoolmanagementsystem.entities.administration.StudentEntity;
import org.example.schoolmanagementsystem.entities.administration.TeacherEntity;
import org.example.schoolmanagementsystem.repositories.GradeRepository;
import org.example.schoolmanagementsystem.repositories.StudentRepository;
import org.example.schoolmanagementsystem.repositories.SubjectRepository;
import org.example.schoolmanagementsystem.repositories.TeacherRepository;
import org.example.schoolmanagementsystem.services.interfaces.GradeService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class GradeServiceImpl implements GradeService {
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;
    private final SubjectRepository subjectRepository;
    private final GradeRepository gradeRepository;

    @Override
    public void assignGradeToStudent(GradeRequestDto dto) {
        StudentEntity student = studentRepository.findById(dto.getStudentId())
                .orElseThrow(() -> new RuntimeException("Student not found"));

        TeacherEntity teacher = teacherRepository.findById(dto.getTeacherId())
                .orElseThrow(() -> new RuntimeException("Teacher not found"));

        SubjectEntity subject = subjectRepository.findById(dto.getSubjectId())
                .orElseThrow(() -> new RuntimeException("Subject not found"));

        GradeEntity grade = new GradeEntity();
        grade.setStudent(student);
        grade.setTeacher(teacher);
        grade.setSubject(subject);
        grade.setGrade(dto.getGrade());
        grade.setDateGiven(LocalDate.now());
        grade.setAcademicYear(dto.getAcademicYear());
        grade.setSemester(dto.getSemester());

        gradeRepository.save(grade);
    }


}
