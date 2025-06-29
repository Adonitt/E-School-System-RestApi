package org.example.schoolmanagementsystem.services.impls;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.example.schoolmanagementsystem.dtos.administration.AdminDetailsDto;
import org.example.schoolmanagementsystem.dtos.administration.AdminDto;
import org.example.schoolmanagementsystem.dtos.administration.AdminListingDto;
import org.example.schoolmanagementsystem.entities.administration.AdminEntity;
import org.example.schoolmanagementsystem.enums.RoleEnum;
import org.example.schoolmanagementsystem.exceptions.EmailExistsException;
import org.example.schoolmanagementsystem.exceptions.PersonalNumberLengthException;
import org.example.schoolmanagementsystem.mappers.AdminMapper;
import org.example.schoolmanagementsystem.repositories.AdminRepository;
import org.example.schoolmanagementsystem.services.interfaces.AdminService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final AdminRepository repository;
    private final AdminMapper mapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AdminDto add(AdminDto dto) {
        validateAdmin(dto);

        AdminEntity admin = mapper.toEntity(dto);

        String encryptedPassword = passwordEncoder.encode(admin.getPassword());
        admin.setPassword(encryptedPassword);

        admin.setCreatedDate(LocalDateTime.now());
        admin.setCreatedBy(AuthServiceImpl.getLoggedInUserEmail() + " - " + AuthServiceImpl.getLoggedInUserRole());

        var savedEntity = repository.save(admin);
        return mapper.toDto(savedEntity);
    }

    private void validateAdmin(AdminDto dto) {
        if (!dto.getPassword().equals(dto.getConfirmPassword())) {
            throw new ValidationException("Passwords do not match.");
        }

        if (repository.existsByEmail(dto.getEmail())) {
            throw new EmailExistsException("Email already exists.");
        }

        if (repository.existsByPersonalNumber(dto.getPersonalNumber())) {
            throw new PersonalNumberLengthException("Personal number already exists.");
        }

        if (dto.getDepartment() == null) {
            throw new ValidationException("Department is required.");
        }

        if (!dto.isAcceptTermsAndConditions()) {
            throw new ValidationException("You must accept the terms and conditions.");
        }

        // Optional:
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
    public void modify(Long id, AdminDto dto) {

        AdminEntity adminFromDb = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Admin with id " + id + " does not exist"));

        if (dto.getPhoto() != null && !dto.getPhoto().isEmpty()) {
            adminFromDb.setPhoto(dto.getPhoto());
        }

        adminFromDb.setPersonalNumber(dto.getPersonalNumber());
        adminFromDb.setName(dto.getName());
        adminFromDb.setSurname(dto.getSurname());
        adminFromDb.setGender(dto.getGender());
        adminFromDb.setBirthDate(dto.getBirthDate());
        adminFromDb.setPhoneNumber(dto.getPhoneNumber());
        adminFromDb.setCity(dto.getCity());
        adminFromDb.setCountry(dto.getCountry());
        adminFromDb.setPostalCode(dto.getPostalCode());
        adminFromDb.setEmail(dto.getEmail());
        adminFromDb.setDepartment(dto.getDepartment());
        adminFromDb.setNotes(dto.getNotes());
        adminFromDb.setModifiedBy(AuthServiceImpl.getLoggedInUserEmail() + " - " + AuthServiceImpl.getLoggedInUserRole());
        adminFromDb.setModifiedDate(LocalDateTime.now());

        var updatedEntity = repository.save(adminFromDb);
        mapper.toDto(updatedEntity);
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
