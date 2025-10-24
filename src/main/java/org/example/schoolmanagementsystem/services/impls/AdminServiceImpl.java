package org.example.schoolmanagementsystem.services.impls;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.example.schoolmanagementsystem.dtos.administration.AdminDetailsDto;
import org.example.schoolmanagementsystem.dtos.administration.AdminDto;
import org.example.schoolmanagementsystem.dtos.administration.AdminListingDto;
import org.example.schoolmanagementsystem.dtos.administration.UpdateAdminDto;
import org.example.schoolmanagementsystem.entities.administration.AdminEntity;
import org.example.schoolmanagementsystem.enums.RoleEnum;
import org.example.schoolmanagementsystem.exceptions.AdminNumberAlreadyExists;
import org.example.schoolmanagementsystem.exceptions.EmailExistsException;
import org.example.schoolmanagementsystem.exceptions.InvalidFormatException;
import org.example.schoolmanagementsystem.exceptions.PersonalNumberLengthException;
import org.example.schoolmanagementsystem.helpers.FileHelper;
import org.example.schoolmanagementsystem.mappers.AdminMapper;
import org.example.schoolmanagementsystem.repositories.AdminRepository;
import org.example.schoolmanagementsystem.repositories.StudentRepository;
import org.example.schoolmanagementsystem.repositories.TeacherRepository;
import org.example.schoolmanagementsystem.services.interfaces.AdminService;
import org.example.schoolmanagementsystem.services.interfaces.EmailService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final AdminRepository repository;
    private final AdminMapper mapper;
    private final PasswordEncoder passwordEncoder;
    private final TeacherRepository teacherRepository;
    private final StudentRepository studentRepository;
    private final EmailService emailService;
    private final FileHelper fileHelper;
    private final AdminRepository adminRepository;

    @Override
    public AdminDto create(AdminDto dto, MultipartFile photo) {
        validateAdmin(dto);

        var filename = "";

        if (photo != null && !photo.isEmpty()) filename = fileHelper.uploadFile(photo);


        AdminEntity admin = mapper.toEntity(dto);

        String encryptedPassword = passwordEncoder.encode(admin.getPassword());
        admin.setPassword(encryptedPassword);

        admin.setCreatedDate(LocalDateTime.now());

        admin.setCreatedBy(AuthServiceImpl.getLoggedInUserEmail() + " - " + AuthServiceImpl.getLoggedInUserRole());

        admin.setRole(RoleEnum.ADMINISTRATOR);
        admin.setAcceptTermsAndConditions(true);
        if (filename.isBlank()) {
            admin.setPhoto("b120c6a6-5948-404e-a9e5-5a198d9ff566_admin.png");
        } else {
            admin.setPhoto(filename);
        }

        emailService.sendWelcomeEmail(dto.getEmail(), dto.getName() + " " + dto.getSurname(), String.valueOf(dto.getRole()), dto.getEmail());
        emailService.sendPasswordChangeEmail(dto.getEmail(), dto.getName(), dto.getPassword());

        var savedEntity = repository.save(admin);
        return mapper.toDto(savedEntity);
    }

    private void validateAdmin(AdminDto dto) {
        if (!dto.getPassword().equals(dto.getConfirmPassword())) {
            throw new ValidationException("Passwords do not match.");
        }

        boolean emailExists = teacherRepository.existsByEmail(dto.getEmail())
                || studentRepository.existsByEmail(dto.getEmail())
                || repository.existsByEmail(dto.getEmail());

        if (emailExists) {
            throw new EmailExistsException("A user with this email already exists.");
        }

        if (adminRepository.existsByAdminNumber(dto.getAdminNumber())) {
            throw new AdminNumberAlreadyExists("This admin number already exists!");
        }

        if (!dto.getPersonalNumber().matches("\\d{10}")) {
            throw new InvalidFormatException("Personal number must contain only digits.");
        }

        boolean personalNumberExists = teacherRepository.existsByPersonalNumber(dto.getPersonalNumber())
                || studentRepository.existsByPersonalNumber(dto.getPersonalNumber())
                || repository.existsByPersonalNumber(dto.getPersonalNumber());

        if (personalNumberExists) {
            throw new PersonalNumberLengthException("A user with this personal number already exists.");
        }
        if (dto.getDepartment() == null) {
            throw new ValidationException("Department is required.");
        }

        if (dto.getRole() != RoleEnum.ADMINISTRATOR) {
            throw new ValidationException("Role must be ADMINISTRATOR.");
        }
    }


    @Override
    public List<AdminListingDto> findAll() {
        var adminsList = repository.findAll();
        return mapper.toListingDto(adminsList);
    }

    @Override
    public AdminDetailsDto findById(Long id) {
        var exists = repository.findById(id);
        if (exists.isEmpty()) {
            throw new EntityNotFoundException("Admin with id " + id + " does not exist");
        }
        return mapper.toAdminDetailsDto(exists.get());
    }

    @Override
    public UpdateAdminDto modify(Long id, UpdateAdminDto dto, MultipartFile photo) {
        AdminEntity adminFromDb = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Admin with id " + id + " does not exist"));

        var filename = "";
        if (photo != null && !photo.isEmpty()) filename = fileHelper.uploadFile(photo);

        adminFromDb.setPersonalNumber(dto.getPersonalNumber());
        adminFromDb.setName(dto.getName());
        adminFromDb.setSurname(dto.getSurname());
        adminFromDb.setGender(dto.getGender());
        adminFromDb.setBirthDate(dto.getBirthDate());
        adminFromDb.setCity(dto.getCity());
        adminFromDb.setCountry(dto.getCountry());
        adminFromDb.setPhoneNumber(dto.getPhoneNumber());
        adminFromDb.setPostalCode(dto.getPostalCode());
        adminFromDb.setEmail(dto.getEmail());
        adminFromDb.setDepartment(dto.getDepartment());
        adminFromDb.setNotes(dto.getNotes());
        adminFromDb.setAddress(dto.getAddress());
        adminFromDb.setRole(dto.getRole());
        adminFromDb.setActive(dto.isActive());
        adminFromDb.setAdminNumber(dto.getAdminNumber());

        if (filename.isBlank()) {
            adminFromDb.setPhoto(adminFromDb.getPhoto());
        } else {
            adminFromDb.setPhoto(filename);
        }

        adminFromDb.setModifiedBy(AuthServiceImpl.getLoggedInUserEmail() + " - " + AuthServiceImpl.getLoggedInUserRole());
        adminFromDb.setModifiedDate(LocalDateTime.now());

        var updatedEntity = repository.save(adminFromDb);
        return mapper.toUpdateDto(updatedEntity);
    }

    @Override
    public void removeById(Long id) {
        var existing = repository.findById(id);
        if (existing.isEmpty()) {
            throw new EntityNotFoundException("Admin with id " + id + " does not exist");
        }
        repository.deleteById(id);
    }
}
