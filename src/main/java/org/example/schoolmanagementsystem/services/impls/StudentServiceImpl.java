package org.example.schoolmanagementsystem.services.impls;

import lombok.RequiredArgsConstructor;
import org.example.schoolmanagementsystem.dtos.student.StudentDto;
import org.example.schoolmanagementsystem.entities.administration.StudentEntity;
import org.example.schoolmanagementsystem.mappers.StudentMapper;
import org.example.schoolmanagementsystem.repositories.StudentRepository;
import org.example.schoolmanagementsystem.services.interfaces.StudentService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {
    private final StudentRepository repository;
    private final StudentMapper mapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public StudentDto add(StudentDto dto) {
        StudentEntity student = mapper.toEntity(dto);

        String encryptedPassword = passwordEncoder.encode(student.getPassword());
        student.setPassword(encryptedPassword);

        var savedEntity = repository.save(student);
        return mapper.toDto(savedEntity);
    }





}
