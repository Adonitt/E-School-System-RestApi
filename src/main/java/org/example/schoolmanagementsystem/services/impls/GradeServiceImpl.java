package org.example.schoolmanagementsystem.services.impls;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.schoolmanagementsystem.dtos.grade.CRDGradeDto;
import org.example.schoolmanagementsystem.dtos.grade.UpdateGradeDto;
import org.example.schoolmanagementsystem.entities.GradeEntity;
import org.example.schoolmanagementsystem.entities.SubjectEntity;
import org.example.schoolmanagementsystem.entities.administration.StudentEntity;
import org.example.schoolmanagementsystem.entities.administration.TeacherEntity;
import org.example.schoolmanagementsystem.exceptions.StudentHasAlreadyANote;
import org.example.schoolmanagementsystem.mappers.GradeMapper;
import org.example.schoolmanagementsystem.repositories.GradeRepository;
import org.example.schoolmanagementsystem.repositories.StudentRepository;
import org.example.schoolmanagementsystem.repositories.SubjectRepository;
import org.example.schoolmanagementsystem.repositories.TeacherRepository;
import org.example.schoolmanagementsystem.services.interfaces.GradeService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GradeServiceImpl implements GradeService {
    private final GradeRepository gradeRepository;
    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;
    private final SubjectRepository subjectRepository;
    private final GradeMapper gradeMapper;

    @Override
    public CRDGradeDto add(CRDGradeDto dto) {
        StudentEntity student = studentRepository.findById(dto.getStudentId())
                .orElseThrow(() -> new EntityNotFoundException("Student not found"));

        SubjectEntity subject = subjectRepository.findById(dto.getSubjectId())
                .orElseThrow(() -> new EntityNotFoundException("Subject not found"));

        TeacherEntity teacher = teacherRepository.findById(3L)
                .orElseThrow(() -> new EntityNotFoundException("Teacher not found"));

        boolean exists = gradeRepository.existsByStudentAndSubjectAndTeacher(student, subject, teacher);
        if (exists) {
            throw new StudentHasAlreadyANote("This student already has a grade for this subject from this teacher.");
        }

        GradeEntity grade = new GradeEntity();
        grade.setTeacher(teacher);
        grade.setStudent(student);
        grade.setSubject(subject);
        grade.setGrade(dto.getGrade());
        grade.setAcademicYear(dto.getAcademicYear());
        grade.setSemester(dto.getSemester());
        grade.setDateGiven(LocalDate.now());

        var savedGrade = gradeRepository.save(grade);
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

        grade.setGrade(dto.getGrade());
        grade.setSemester(dto.getSemester());
        grade.setAcademicYear(dto.getAcademicYear());

        var savedGrade = gradeRepository.save(grade);
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
    public Map<String, List<CRDGradeDto>> groupBySemester() {
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
                .map(gradeMapper::toDto)
                .collect(Collectors.groupingBy(CRDGradeDto::getTeacher));
    }


}
