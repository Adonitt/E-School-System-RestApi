package org.example.schoolmanagementsystem.services.impls;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.example.schoolmanagementsystem.dtos.student.CreateStudentDto;
import org.example.schoolmanagementsystem.dtos.student.StudentListingDto;
import org.example.schoolmanagementsystem.dtos.student.UpdateStudentDto;
import org.example.schoolmanagementsystem.entities.administration.StudentEntity;
import org.example.schoolmanagementsystem.enums.RoleEnum;
import org.example.schoolmanagementsystem.exceptions.EmailExistsException;
import org.example.schoolmanagementsystem.exceptions.InvalidFormatException;
import org.example.schoolmanagementsystem.exceptions.PersonalNumberLengthException;
import org.example.schoolmanagementsystem.mappers.StudentMapper;
import org.example.schoolmanagementsystem.repositories.AdminRepository;
import org.example.schoolmanagementsystem.repositories.StudentRepository;
import org.example.schoolmanagementsystem.repositories.TeacherRepository;
import org.example.schoolmanagementsystem.services.interfaces.StudentService;
import org.hibernate.sql.Update;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {
    private final TeacherRepository teacherRepository;
    private final StudentRepository repository;
    private final AdminRepository adminRepository;

    private final StudentMapper mapper;
    private final PasswordEncoder passwordEncoder;


    @Override
    public CreateStudentDto add(CreateStudentDto dto) {
        validateStudent(dto);

        var student = mapper.toEntity(dto);
        String encryptedPassword = passwordEncoder.encode(student.getPassword());
        student.setPassword(encryptedPassword);
        student.setCreatedBy(AuthServiceImpl.getLoggedInUserEmail() + " - " + AuthServiceImpl.getLoggedInUserRole());
        student.setCreatedDate(LocalDateTime.now());
        student.setRole(RoleEnum.STUDENT);
        var savedStudent = repository.save(student);
        return mapper.toDto(savedStudent);
    }

    private void validateStudent(CreateStudentDto dto) {
        if (!dto.getPassword().equals(dto.getConfirmPassword())) {
            throw new ValidationException("Passwords do not match.");
        }

        // Email validation against all repositories
        boolean emailExists = teacherRepository.existsByEmail(dto.getEmail())
                || repository.existsByEmail(dto.getEmail())
                || adminRepository.existsByEmail(dto.getEmail());

        if (emailExists) {
            throw new EmailExistsException("A user with this email already exists.");
        }

        if (!dto.getPersonalNumber().matches("\\d{10}")) {
            throw new InvalidFormatException("Personal number must contain only digits.");
        }

        // Personal number validation against all repositories
        boolean personalNumberExists = teacherRepository.existsByPersonalNumber(dto.getPersonalNumber())
                || repository.existsByPersonalNumber(dto.getPersonalNumber())
                || adminRepository.existsByPersonalNumber(dto.getPersonalNumber());

        if (personalNumberExists) {
            throw new PersonalNumberLengthException("A user with this personal number already exists.");
        }

        if (dto.getBirthDate().isAfter(LocalDate.now().minusYears(5))) {
            throw new ValidationException("Student must be at least 5 years old.");
        }

        if (dto.getRegisteredDate() != null && dto.getRegisteredDate().isBefore(dto.getBirthDate())) {
            throw new ValidationException("Registration date cannot be before birth date.");
        }

        if (dto.getEmail() != null && dto.getEmail().equalsIgnoreCase(dto.getGuardianEmail())) {
            throw new ValidationException("Guardian email must differ from student email.");
        }

        if (!dto.getAcademicYear().matches("^[0-9]{4}-[0-9]{4}$")) {
            throw new ValidationException("Academic year must be in format YYYY-YYYY.");
        }
    }


    @Override
    public List<StudentListingDto> findAll() {
        var studentsList = repository.findAll();
        return mapper.toListingDto(studentsList);
    }

    @Override
    public CreateStudentDto findById(Long id) {
        var exists = repository.findById(id).orElseThrow(() -> new RuntimeException("Student with id " + id + " does not exist"));
        return mapper.toDto(exists);
    }

    @Override
    public UpdateStudentDto modify(Long id, UpdateStudentDto dto) {
        StudentEntity studentFromDb = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Student with id " + id + " does not exist"));

        studentFromDb.setPersonalNumber(dto.getPersonalNumber());
        studentFromDb.setName(dto.getName());
        studentFromDb.setSurname(dto.getSurname());
        studentFromDb.setGender(dto.getGender());
        studentFromDb.setBirthDate(dto.getBirthDate());
        studentFromDb.setAddress(dto.getAddress());
        studentFromDb.setCity(dto.getCity());
        studentFromDb.setCountry(dto.getCountry());
        studentFromDb.setPostalCode(dto.getPostalCode());
        studentFromDb.setPhoneNumber(dto.getPhoneNumber());
        studentFromDb.setNotes(dto.getNotes());
        studentFromDb.setEmail(dto.getEmail());
        studentFromDb.setPhoto(dto.getPhoto());

        studentFromDb.setAcademicYear(dto.getAcademicYear());
        studentFromDb.setCurrentSemester(dto.getCurrentSemester());
        studentFromDb.setGraduated(dto.isGraduated());
        studentFromDb.setActive(dto.isActive());

        studentFromDb.setGuardianName(dto.getGuardianName());
        studentFromDb.setGuardianPhoneNumber(dto.getGuardianPhoneNumber());
        studentFromDb.setGuardianEmail(dto.getGuardianEmail());
        studentFromDb.setEmergencyContactPhone(dto.getEmergencyContactPhone());
        studentFromDb.setRelationshipToStudent(dto.getRelationshipToStudent());

        studentFromDb.setUpdatedBy(AuthServiceImpl.getLoggedInUserEmail() + " - " + AuthServiceImpl.getLoggedInUserRole());
        studentFromDb.setUpdatedDate(LocalDateTime.now());

        var savedStudent = repository.save(studentFromDb);
        return mapper.toUpdateDto(savedStudent);
    }


    @Override
    public void removeById(Long id) {
        findById(id);
        repository.deleteById(id);
    }
}
