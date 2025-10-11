package org.example.schoolmanagementsystem.services.impls;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.schoolmanagementsystem.dtos.subject.CreateSubjectDto;
import org.example.schoolmanagementsystem.dtos.subject.SubjectDto;
import org.example.schoolmanagementsystem.dtos.subject.UpdateSubjectDto;
import org.example.schoolmanagementsystem.entities.SubjectEntity;
import org.example.schoolmanagementsystem.entities.administration.StudentEntity;
import org.example.schoolmanagementsystem.entities.administration.TeacherEntity;
import org.example.schoolmanagementsystem.enums.SemesterEnum;
import org.example.schoolmanagementsystem.mappers.SubjectMapper;
import org.example.schoolmanagementsystem.repositories.StudentRepository;
import org.example.schoolmanagementsystem.repositories.SubjectRepository;
import org.example.schoolmanagementsystem.repositories.TeacherRepository;
import org.example.schoolmanagementsystem.services.interfaces.SubjectService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SubjectServiceImpl implements SubjectService {
    private final SubjectRepository subjectRepository;
    private final TeacherRepository teacherRepository;
    private final SubjectMapper subjectMapper;
    private final StudentRepository studentRepository;

    @Override
    public CreateSubjectDto add(CreateSubjectDto dto) {
        if (dto.getTeachers() == null || dto.getTeachers().isEmpty()) {
            throw new EntityNotFoundException("teacherIds must not be null or empty");
        }

        List<TeacherEntity> teachers = teacherRepository.findAllById(dto.getTeachers());

        if (teachers.size() != dto.getTeachers().size()) {
            throw new EntityNotFoundException("Some teacher IDs do not exist: " + dto.getTeachers());
        }

        var subject = subjectMapper.fromCreateDto(dto);

        subject.setTeachers(teachers);

        System.out.println("Received semester list: " + dto.getSemester());
        subject.setSemester(dto.getSemester());

        if (dto.getCredits() <= 0) {
            throw new IllegalArgumentException("Credits must be greater than 0");
        }

        if (dto.getTotalHours() <= 0) {
            throw new IllegalArgumentException("Total hours must be greater than 0");
        }

        List<StudentEntity> studentsInClass = studentRepository.findByClassNumberIn(dto.getClassNumber());

        subject.setStudents(studentsInClass);

        for (TeacherEntity teacher : teachers) {
            if (teacher.getSubjects() == null) {
                teacher.setSubjects(new ArrayList<>());
            }
            if (!teacher.getSubjects().contains(subject)) {
                teacher.getSubjects().add(subject);
            }
        }

        subject.setClassNumber(dto.getClassNumber());

        for (StudentEntity student : studentsInClass) {
            if (student.getSubjects() == null) {
                student.setSubjects(new ArrayList<>());
            }
            if (!student.getSubjects().contains(subject)) {
                student.getSubjects().add(subject);
            }
        }

        var savedSubject = subjectRepository.save(subject);
        studentRepository.saveAll(studentsInClass);

        return subjectMapper.toDto(savedSubject);
    }

    @Override
    public List<SubjectDto> findAll() {
        var subjects = subjectRepository.findAll();
        return subjectMapper.toListingDtoList(subjects);
    }

    @Override
    public SubjectDto findById(Long id) {
        SubjectEntity subject = subjectRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Subject not found with id " + id));
        return subjectMapper.toDetailsDto(subject);
    }

    @Override
    public UpdateSubjectDto modify(Long id, UpdateSubjectDto dto) {
        SubjectEntity subjectFromDb = subjectRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Subject not found with id " + id));

        List<TeacherEntity> teachers = teacherRepository.findAllById(dto.getTeachers());
        if (teachers.isEmpty()) {
            throw new EntityNotFoundException("Teachers not found for given IDs");
        }
        subjectFromDb.setTeachers(teachers);

        for (TeacherEntity oldTeacher : subjectFromDb.getTeachers()) {
            oldTeacher.getSubjects().remove(subjectFromDb);
        }

        subjectFromDb.setTeachers(teachers);

        for (TeacherEntity newTeacher : teachers) {
            if (newTeacher.getSubjects() == null) {
                newTeacher.setSubjects(new ArrayList<>());
            }
            if (!newTeacher.getSubjects().contains(subjectFromDb)) {
                newTeacher.getSubjects().add(subjectFromDb);
            }
        }

        subjectFromDb.setName(dto.getName());
        subjectFromDb.setDescription(dto.getDescription());
        subjectFromDb.setSemester(dto.getSemester());
        subjectFromDb.setCredits(dto.getCredits());
        subjectFromDb.setTotalHours(dto.getTotalHours());
        subjectFromDb.setClassNumber(dto.getClassNumber());

        if (dto.getClassNumber() != null) {
            List<StudentEntity> previousStudents = subjectFromDb.getStudents();
            List<StudentEntity> currentStudents = studentRepository.findByClassNumberIn(dto.getClassNumber());

            Set<Long> newStudentIds = currentStudents.stream().map(StudentEntity::getId).collect(Collectors.toSet());

            for (StudentEntity oldStudent : previousStudents) {
                if (!newStudentIds.contains(oldStudent.getId())) {
                    oldStudent.getSubjects().remove(subjectFromDb);
                }
            }

            subjectFromDb.setStudents(currentStudents);
            for (StudentEntity student : currentStudents) {
                if (student.getSubjects() == null) {
                    student.setSubjects(new ArrayList<>());
                }
                if (!student.getSubjects().contains(subjectFromDb)) {
                    student.getSubjects().add(subjectFromDb);
                }
            }

            studentRepository.saveAll(previousStudents);
            studentRepository.saveAll(currentStudents);
        }

        SubjectEntity updated = subjectRepository.save(subjectFromDb);
        return subjectMapper.toUpdateDto(updated);
    }


    @Override
    @Transactional
    public void removeById(Long id) {
        SubjectEntity subject = subjectRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Subject with id " + id + " not found"));

        if (subject.getTeachers() != null) {
            subject.getTeachers().forEach(teacher -> teacher.getSubjects().remove(subject));
        }

        if (subject.getStudents() != null) {
            subject.getStudents().forEach(student -> student.getSubjects().remove(subject));
        }

        subjectRepository.delete(subject);
    }



    public List<SubjectDto> findBySemester(SemesterEnum semester) {
        List<SubjectEntity> subjects = subjectRepository.findBySemester(semester);
        return subjects.stream().map(subjectMapper::toListingDto).collect(Collectors.toList());
    }

    public List<Integer> getAllClassNumbers() {
        Set<Integer> classNumbers = new HashSet<>();
        classNumbers.addAll(subjectRepository.findDistinctClassNumbers());
        classNumbers.addAll(studentRepository.findDistinctClassNumbers());
        return new ArrayList<>(classNumbers);
    }

}

