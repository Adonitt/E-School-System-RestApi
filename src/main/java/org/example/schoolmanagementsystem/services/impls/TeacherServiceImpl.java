package org.example.schoolmanagementsystem.services.impls;

import lombok.RequiredArgsConstructor;
import org.example.schoolmanagementsystem.dtos.teacher.TeacherDto;
import org.example.schoolmanagementsystem.entities.administration.TeacherEntity;
import org.example.schoolmanagementsystem.mappers.TeacherMapper;
import org.example.schoolmanagementsystem.repositories.TeacherRepository;
import org.example.schoolmanagementsystem.services.interfaces.TeacherService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TeacherServiceImpl implements TeacherService {

    private final TeacherRepository repository;
    private final TeacherMapper mapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public TeacherDto add(TeacherDto dto) {
        TeacherEntity teacher = mapper.toEntity(dto);

        String encryptedPassword = passwordEncoder.encode(teacher.getPassword());
        teacher.setPassword(encryptedPassword);

        var savedEntity = repository.save(teacher);
        return mapper.toDto(savedEntity);
    }

}
