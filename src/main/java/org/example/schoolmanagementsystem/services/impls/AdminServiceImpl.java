package org.example.schoolmanagementsystem.services.impls;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.schoolmanagementsystem.dtos.administration.AdminDetailsDto;
import org.example.schoolmanagementsystem.dtos.administration.AdminDto;
import org.example.schoolmanagementsystem.dtos.administration.AdminListingDto;
import org.example.schoolmanagementsystem.entities.administration.AdminEntity;
import org.example.schoolmanagementsystem.mappers.AdminMapper;
import org.example.schoolmanagementsystem.repositories.AdminRepository;
import org.example.schoolmanagementsystem.services.interfaces.AdminService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final AdminRepository repository;
    private final AdminMapper mapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AdminDto add(AdminDto dto) {
        AdminEntity admin = mapper.toEntity(dto);

        String encryptedPassword = passwordEncoder.encode(admin.getPassword());
        admin.setPassword(encryptedPassword);

        var savedEntity = repository.save(admin);
        return mapper.toDto(savedEntity);
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

        AdminEntity admin = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Admin with id " + id + " does not exist"));

        if (dto.getPhoto() != null && !dto.getPhoto().isEmpty()) {
            admin.setPhoto(dto.getPhoto());
        }

        admin.setPersonalNumber(dto.getPersonalNumber());
        admin.setName(dto.getName());
        admin.setSurname(dto.getSurname());
        admin.setGender(dto.getGender());
        admin.setBirthDate(dto.getBirthDate());
        admin.setPhoneNumber(dto.getPhoneNumber());
        admin.setCity(dto.getCity());
        admin.setCountry(dto.getCountry());
        admin.setPostalCode(dto.getPostalCode());
        admin.setEmail(dto.getEmail());
        admin.setDepartment(dto.getDepartment());
        admin.setNotes(dto.getNotes());
        admin.setModifiedBy(dto.getModifiedBy());
        admin.setModifiedDate(dto.getModifiedDate());

        repository.save(admin);
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
