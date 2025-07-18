package org.example.schoolmanagementsystem.services.impls;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.schoolmanagementsystem.dtos.attendance.CRDAttendanceDto;
import org.example.schoolmanagementsystem.dtos.attendance.UpdateAttendanceDto;
import org.example.schoolmanagementsystem.entities.AttendanceEntity;
import org.example.schoolmanagementsystem.entities.SubjectEntity;
import org.example.schoolmanagementsystem.entities.administration.StudentEntity;
import org.example.schoolmanagementsystem.exceptions.AttendanceAlreadyExistsException;
import org.example.schoolmanagementsystem.mappers.AttendanceMapper;
import org.example.schoolmanagementsystem.repositories.AttendanceRepository;
import org.example.schoolmanagementsystem.repositories.StudentRepository;
import org.example.schoolmanagementsystem.repositories.SubjectRepository;
import org.example.schoolmanagementsystem.services.interfaces.AttendanceService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AttendanceServiceImpl implements AttendanceService {

    private final StudentRepository studentRepository;
    private final AttendanceRepository attendanceRepository;
    private final SubjectRepository subjectRepository;
    private final AttendanceMapper mapper;


    @Override
    public CRDAttendanceDto add(CRDAttendanceDto dto) {
        if (attendanceRepository.existsByStudentIdAndSubjectIdAndDate(dto.getStudentId(), dto.getSubjectId(), LocalDate.now())) {
            throw new AttendanceAlreadyExistsException("Attendance already exists for this student, subject, and date.");
        }

        StudentEntity student = studentRepository.findById(dto.getStudentId()).orElseThrow(() -> new EntityNotFoundException("Student not found"));

        SubjectEntity subject = subjectRepository.findById(dto.getSubjectId()).orElseThrow(() -> new EntityNotFoundException("Subject not found"));

        AttendanceEntity attendance = new AttendanceEntity();
        attendance.setStudent(student);
        attendance.setSubject(subject);
        attendance.setDate(LocalDate.now());
        attendance.setPresent(dto.getPresent());
        attendance.setNotes(dto.getNotes());

        var savedAttendance = attendanceRepository.save(attendance);
        return mapper.toDto(savedAttendance);
    }

    @Override
    public List<CRDAttendanceDto> findAll() {
        var attendancesList = attendanceRepository.findAll();
        return mapper.toDtoList(attendancesList);
    }

    @Override
    public CRDAttendanceDto findById(Long id) {
        AttendanceEntity attendance = attendanceRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Attendance not found"));
        return mapper.toDto(attendance);
    }

    @Override
    public UpdateAttendanceDto modify(Long id, UpdateAttendanceDto dto) {
        var attendance = attendanceRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Attendance not found"));

        attendance.setPresent(dto.getPresent());
        attendance.setNotes(dto.getNotes());

        var savedAttendance = attendanceRepository.save(attendance);
        return mapper.toUpdateDto(savedAttendance);
    }

    @Override
    public void removeById(Long id) {
        if (!attendanceRepository.existsById(id)) {
            throw new EntityNotFoundException("Attendance not found with id: " + id);
        }
        attendanceRepository.deleteById(id);
    }


    @Override
    public List<CRDAttendanceDto> getAttendanceBySubject(Long subjectId) {
        return attendanceRepository.findAll()
                .stream().
                filter(a -> a.getSubject().getId().equals(subjectId)).
                map(mapper::toDto).collect(Collectors.toList());
    }
}
