package org.example.schoolmanagementsystem.services.impls;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.example.schoolmanagementsystem.dtos.student.CreateStudentDto;
import org.example.schoolmanagementsystem.dtos.student.StudentDetailsDto;
import org.example.schoolmanagementsystem.dtos.student.StudentListingDto;
import org.example.schoolmanagementsystem.dtos.student.UpdateStudentDto;
import org.example.schoolmanagementsystem.entities.SubjectEntity;
import org.example.schoolmanagementsystem.entities.administration.StudentEntity;
import org.example.schoolmanagementsystem.entities.administration.TeacherEntity;
import org.example.schoolmanagementsystem.enums.RoleEnum;
import org.example.schoolmanagementsystem.enums.SemesterEnum;
import org.example.schoolmanagementsystem.exceptions.EmailExistsException;
import org.example.schoolmanagementsystem.exceptions.InvalidFormatException;
import org.example.schoolmanagementsystem.exceptions.PersonalNumberLengthException;
import org.example.schoolmanagementsystem.helpers.FileHelper;
import org.example.schoolmanagementsystem.mappers.StudentMapper;
import org.example.schoolmanagementsystem.repositories.AdminRepository;
import org.example.schoolmanagementsystem.repositories.StudentRepository;
import org.example.schoolmanagementsystem.repositories.SubjectRepository;
import org.example.schoolmanagementsystem.repositories.TeacherRepository;
import org.example.schoolmanagementsystem.services.interfaces.EmailService;
import org.example.schoolmanagementsystem.services.interfaces.StudentService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {
    private final TeacherRepository teacherRepository;
    private final StudentRepository repository;
    private final AdminRepository adminRepository;
    private final SubjectRepository subjectRepository;
    private final StudentMapper mapper;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final FileHelper fileHelper;
    private final StudentMapper studentMapper;

    @Override
    public CreateStudentDto create(CreateStudentDto dto, MultipartFile photo) {
        validateStudent(dto);

        var filename = "";
        if (photo != null && !photo.isEmpty()) filename = fileHelper.uploadFile(photo);

        var student = mapper.toEntity(dto);
        String encryptedPassword = passwordEncoder.encode(student.getPassword());

        student.setPassword(encryptedPassword);
        student.setCreatedBy(AuthServiceImpl.getLoggedInUserEmail() + " - " + AuthServiceImpl.getLoggedInUserRole());
        student.setCreatedDate(LocalDateTime.now());
        student.setActive(true);
        student.setRole(RoleEnum.STUDENT);
        student.setCurrentSemester(SemesterEnum.SEMESTER_1);
        student.setRegisteredDate(LocalDate.now());

        if (filename.isBlank()) {
            student.setPhoto("c65d7951-efc1-48ba-80aa-08f8bb27e688_student.webp");
        } else {
            student.setPhoto(filename);
        }

//        emailService.sendWelcomeEmail(dto.getEmail(), dto.getName() + " " + dto.getSurname(), String.valueOf(dto.getRole()), dto.getEmail());
//        emailService.sendPasswordChangeEmail(dto.getEmail(), dto.getName(), dto.getPassword());

        var savedStudent = repository.save(student);
        return mapper.toDto(savedStudent);
    }

    private void validateStudent(CreateStudentDto dto) {
        if (!dto.getPassword().equals(dto.getConfirmPassword())) {
            throw new ValidationException("Passwords do not match.");
        }

        boolean emailExists = teacherRepository.existsByEmail(dto.getEmail()) || repository.existsByEmail(dto.getEmail()) || adminRepository.existsByEmail(dto.getEmail());

        boolean personalNumberExists = teacherRepository.existsByPersonalNumber(dto.getPersonalNumber()) || repository.existsByPersonalNumber(dto.getPersonalNumber()) || adminRepository.existsByPersonalNumber(dto.getPersonalNumber());

        if (personalNumberExists) {
            throw new PersonalNumberLengthException("A user with this personal number already exists.");
        }

        if (emailExists) {
            throw new EmailExistsException("A user with this email already exists.");
        }

        if (!dto.getPersonalNumber().matches("\\d{10}")) {
            throw new InvalidFormatException("Personal number must contain only digits.");
        }

        if (dto.getBirthDate().isAfter(LocalDate.now().minusYears(18))) {
            throw new ValidationException("Student must be at least 18 years old.");
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
        return mapper.toListingDtoList(studentsList);
    }

    @Override
    public StudentDetailsDto findById(Long id) {
        var exists = repository.findById(id).orElseThrow(() -> new RuntimeException("Student with id " + id + " does not exist"));
        return mapper.toDetailsDto(exists);
    }

    @Override
    public UpdateStudentDto modify(Long id, UpdateStudentDto dto, MultipartFile photo) {
        StudentEntity studentFromDb = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Student with id " + id + " does not exist"));

        var filename = "";
        if (photo != null && !photo.isEmpty()) filename = fileHelper.uploadFile(photo);

        boolean personalNumberExistsForOther = repository.existsByPersonalNumber(dto.getPersonalNumber()) && !dto.getPersonalNumber().equals(studentFromDb.getPersonalNumber());

        if (personalNumberExistsForOther) {
            throw new ValidationException("Personal number already used by another student");
        }
        if (dto.getBirthDate().isAfter(LocalDate.now().minusYears(18))) {
            throw new ValidationException("Student must be at least 18 years old.");
        }

        if (dto.getEmail() != null && dto.getEmail().equalsIgnoreCase(dto.getGuardianEmail())) {
            throw new ValidationException("Guardian email must differ from student email.");
        }

        if (!dto.getAcademicYear().matches("^[0-9]{4}-[0-9]{4}$")) {
            throw new ValidationException("Academic year must be in format YYYY-YYYY.");
        }

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

        studentFromDb.setAcademicYear(dto.getAcademicYear());
        studentFromDb.setCurrentSemester(dto.getCurrentSemester());
        studentFromDb.setGraduated(dto.isGraduated());
        studentFromDb.setActive(dto.isActive());
        studentFromDb.setClassNumber(dto.getClassNumber());

        studentFromDb.setGuardianName(dto.getGuardianName());
        studentFromDb.setGuardianPhoneNumber(dto.getGuardianPhoneNumber());
        studentFromDb.setGuardianEmail(dto.getGuardianEmail());
        studentFromDb.setEmergencyContactPhone(dto.getEmergencyContactPhone());
        studentFromDb.setRelationshipToStudent(dto.getRelationshipToStudent());

        if (filename.isBlank()) {
            studentFromDb.setPhoto(studentFromDb.getPhoto());
        } else {
            studentFromDb.setPhoto(filename);
        }

        studentFromDb.setModifiedBy(AuthServiceImpl.getLoggedInUserEmail() + " - " + AuthServiceImpl.getLoggedInUserRole());
        studentFromDb.setModifiedDate(LocalDateTime.now());

        // TODO: send update email

        var savedStudent = repository.save(studentFromDb);
        return mapper.toUpdateDto(savedStudent);
    }


    @Override
    public List<StudentDetailsDto> getStudentsByClass(int classNumber) {
        return repository.findByClassNumber(classNumber).stream().map(mapper::toDetailsDto).collect(Collectors.toList());
    }

    @Override
    public List<StudentDetailsDto> getStudentsForTeacherAndSubject(String teacherEmail, Long subjectId) {
        TeacherEntity teacher = teacherRepository.findByEmail(teacherEmail).orElseThrow(() -> new EntityNotFoundException("Teacher not found"));

        SubjectEntity subject = subjectRepository.findById(subjectId).orElseThrow(() -> new EntityNotFoundException("Subject not found"));

        if (!teacher.getSubjects().contains(subject)) {
            throw new ValidationException("Teacher does not teach this subject");
        }

        List<SemesterEnum> semesters = subject.getSemester();

        List<StudentEntity> students = repository.findByCurrentSemesterIn(semesters);

        return studentMapper.toDtoList(students);
    }

    @Override
    public String deactivateStudent(Long id) {
        StudentEntity student = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Student not found"));
        if (!student.isActive()) {
            return "Student is already deactivated";
        }
        student.setActive(false);
        repository.save(student);

        return "Student deactivated successfully";
    }

    @Override
    public String activateStudent(Long id) {
        StudentEntity student = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Student not found!"));

        if (student.isActive()) {
            return "Student is already active!";
        }
        student.setActive(true);
        repository.save(student);
        return "Student activated successfully!";

    }


}
