package org.example.schoolmanagementsystem.services.impls;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;
import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;
import org.example.schoolmanagementsystem.dtos.teacher.CreateTeacherDto;
import org.example.schoolmanagementsystem.dtos.teacher.TeacherDetailsDto;
import org.example.schoolmanagementsystem.dtos.teacher.TeacherListingDto;
import org.example.schoolmanagementsystem.dtos.teacher.UpdateTeacherDto;
import org.example.schoolmanagementsystem.entities.administration.TeacherEntity;
import org.example.schoolmanagementsystem.exceptions.*;
import org.example.schoolmanagementsystem.mappers.TeacherMapper;
import org.example.schoolmanagementsystem.repositories.TeacherRepository;
import org.example.schoolmanagementsystem.services.interfaces.TeacherService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TeacherServiceImpl implements TeacherService {
    private final TeacherRepository repository;
    private final TeacherMapper mapper;
    private final PasswordEncoder passwordEncoder;


    @Override
    public CreateTeacherDto add(CreateTeacherDto dto) {
        validateTeacher(dto);

        TeacherEntity teacher = mapper.toEntity(dto);

        String encryptedPassword = passwordEncoder.encode(teacher.getPassword());
        teacher.setPassword(encryptedPassword);

        var savedEntity = repository.save(teacher);
        return mapper.toDto(savedEntity);
    }

    private void validateTeacher(CreateTeacherDto dto) {
        if (!dto.getPassword().equals(dto.getConfirmPassword())) {
            throw new ValidationException("Passwords do not match");
        }

        if (repository.existsByEmail(dto.getEmail())) {
            throw new EmailExistsException("A teacher with this email already exists.");
        }

        if (!dto.getPersonalNumber().matches("\\d{10}")) {
            throw new InvalidFormatException("Personal number must contain only digits.");
        }

        if (repository.existsByPersonalNumber(dto.getPersonalNumber())) {
            throw new PersonalNumberLengthException("A teacher with this personal number already exists.");
        }

        if (dto.getBirthDate().isAfter(LocalDate.now().minusYears(18))) {
            throw new InvalidDataException("Teacher must be at least 21 years old.");
        }

        if (dto.getEmploymentDate().isAfter(LocalDate.now())) {
            throw new InvalidDataException("Employment date cannot be in the future.");
        }

        int calculatedYears = Period.between(dto.getEmploymentDate(), LocalDate.now()).getYears();
        if (dto.getYearsOfExperience() > calculatedYears) {
            throw new InvalidDataException("Years of experience cannot exceed employment duration.");
        }

        if (dto.getSpecialization() == null || dto.getSpecialization().isBlank()) {
            throw new MissingFieldException("Specialization is required.");
        }

        if (dto.getSalary() < 300 || dto.getSalary() > 10000) {
            throw new InvalidDataException("Salary must be between 300 and 10,000.");
        }


    }

    @Override
    public List<TeacherListingDto> findAll() {
        var teachersList = repository.findAll();
        return mapper.toListingDto(teachersList);
    }

    @Override
    public TeacherDetailsDto findById(Long id) {
        var exists = repository.findById(id);
        if (exists.isEmpty()) {
            throw new EntityNotFoundException("Teacher with id " + id + " does not exist");
        }
        return mapper.toDetailsDto(exists.get());
    }

    @Override
    public void modify(Long id, UpdateTeacherDto dto) {
        TeacherEntity teacherFromDb = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Teacher with id " + id + " does not exist"));

        teacherFromDb.setPersonalNumber(dto.getPersonalNumber());
        teacherFromDb.setName(dto.getName());
        teacherFromDb.setSurname(dto.getSurname());
        teacherFromDb.setGender(dto.getGender());
        teacherFromDb.setBirthDate(dto.getBirthDate());
        teacherFromDb.setAddress(dto.getAddress());
        teacherFromDb.setCity(dto.getCity());
        teacherFromDb.setCountry(dto.getCountry());
        teacherFromDb.setPostalCode(dto.getPostalCode());
        teacherFromDb.setPhoneNumber(dto.getPhoneNumber());
        teacherFromDb.setRole(dto.getRole());
        teacherFromDb.setNotes(dto.getNotes());
        teacherFromDb.setEmail(dto.getEmail());

        teacherFromDb.setSpecialization(dto.getSpecialization());
        teacherFromDb.setYearsOfExperience(dto.getYearsOfExperience());
        teacherFromDb.setSalary(dto.getSalary());
        teacherFromDb.setEmploymentDate(dto.getEmploymentDate());
        teacherFromDb.setQualification(dto.getQualification());

        var updatedEntity = repository.save(teacherFromDb);
        mapper.toUpdateDto(updatedEntity);
    }

    @Override
    public void removeById(Long id) {
        findById(id);
        repository.deleteById(id);
    }


}
