package org.example.schoolmanagementsystem.services.impls;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.schoolmanagementsystem.dtos.subject.CreateSubjectDto;
import org.example.schoolmanagementsystem.dtos.subject.SubjectDto;
import org.example.schoolmanagementsystem.dtos.subject.UpdateSubjectDto;
import org.example.schoolmanagementsystem.entities.SubjectEntity;
import org.example.schoolmanagementsystem.entities.administration.TeacherEntity;
import org.example.schoolmanagementsystem.mappers.SubjectMapper;
import org.example.schoolmanagementsystem.repositories.SubjectRepository;
import org.example.schoolmanagementsystem.repositories.TeacherRepository;
import org.example.schoolmanagementsystem.services.interfaces.SubjectService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SubjectServiceImpl implements SubjectService {
    private final SubjectRepository subjectRepository;
    private final TeacherRepository teacherRepository;
    private final SubjectMapper subjectMapper;

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

        for (TeacherEntity teacher : teachers) {
            if (teacher.getSubjects() == null) {
                teacher.setSubjects(new ArrayList<>());
            }
            if (!teacher.getSubjects().contains(subject)) {
                teacher.getSubjects().add(subject);
            }
        }

        var savedSubject = subjectRepository.save(subject);

        return subjectMapper.toDto(savedSubject);
    }


    @Override
    public List<SubjectDto> findAll() {
        return subjectRepository.findAll()
                .stream()
                .map(subjectMapper::toListingDto)
                .toList();
    }

    @Override
    public SubjectDto findById(Long id) {
        SubjectEntity subject = subjectRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Subject not found with id " + id));
        return subjectMapper.toDetailsDto(subject);

    }

    @Override
    public UpdateSubjectDto modify(Long id, UpdateSubjectDto dto) {
        SubjectEntity subjectFromDb = subjectRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Subject not found with id " + id));

        subjectFromDb.setName(dto.getName());
        subjectFromDb.setDescription(dto.getDescription());

        List<TeacherEntity> teachers = teacherRepository.findAllById(dto.getTeacherIds());
        if (teachers.isEmpty()) {
            throw new EntityNotFoundException("Teachers not found for given IDs");
        }
        subjectFromDb.setTeachers(teachers);

        var updated = subjectRepository.save(subjectFromDb);
        return subjectMapper.toUpdateDto(updated);
    }

    @Override
    @Transactional
    public void removeById(Long id) {
        SubjectEntity subject = subjectRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Subject with id " + id + " not found"));

        // Break relationship on owning side
        List<TeacherEntity> teachers = subject.getTeachers();
        if (teachers != null) {
            for (TeacherEntity teacher : teachers) {
                teacher.getSubjects().remove(subject);
                teacherRepository.save(teacher);
            }
        }

        subjectRepository.delete(subject);
    }

}
