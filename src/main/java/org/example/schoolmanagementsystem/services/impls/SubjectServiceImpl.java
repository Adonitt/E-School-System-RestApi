package org.example.schoolmanagementsystem.services.impls;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.schoolmanagementsystem.dtos.subject.CreateSubjectDto;
import org.example.schoolmanagementsystem.dtos.subject.SubjectDetailsDto;
import org.example.schoolmanagementsystem.dtos.subject.SubjectListingDto;
import org.example.schoolmanagementsystem.dtos.subject.UpdateSubjectDto;
import org.example.schoolmanagementsystem.entities.SubjectEntity;
import org.example.schoolmanagementsystem.entities.administration.TeacherEntity;
import org.example.schoolmanagementsystem.mappers.SubjectMapper;
import org.example.schoolmanagementsystem.repositories.SubjectRepository;
import org.example.schoolmanagementsystem.repositories.TeacherRepository;
import org.example.schoolmanagementsystem.services.interfaces.SubjectService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SubjectServiceImpl implements SubjectService {
    private final SubjectRepository subjectRepository;
    private final TeacherRepository teacherRepository;
    private final SubjectMapper subjectMapper;

    @Override
    public CreateSubjectDto add(CreateSubjectDto dto) {
        List<TeacherEntity> teachers = teacherRepository.findAllById(dto.getTeacherIds());
        if (teachers.isEmpty()) {
            throw new EntityNotFoundException("Teacher with id " + dto.getTeacherIds() + " does not exist.");
        }

        var subject = subjectMapper.fromCreateDto(dto);
        subject.setTeachers(teachers);

        var savedSubject = subjectRepository.save(subject);
        return subjectMapper.toDto(savedSubject);
    }

    @Override
    public List<SubjectListingDto> findAll() {
        return subjectRepository.findAll()
                .stream()
                .map(subjectMapper::toListingDto)
                .toList();
    }

    @Override
    public SubjectDetailsDto findById(Long id) {
        SubjectEntity subject = subjectRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Subject not found with id " + id));
        return subjectMapper.toDetailsDto(subject);

    }

    @Override
    public void modify(Long id, UpdateSubjectDto dto) {
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
        subjectMapper.toUpdateDto(updated);
    }

    @Override
    public void removeById(Long id) {
        subjectRepository.deleteById(id);
    }
}
