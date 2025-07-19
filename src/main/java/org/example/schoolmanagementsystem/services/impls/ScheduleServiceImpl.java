package org.example.schoolmanagementsystem.services.impls;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.schoolmanagementsystem.dtos.schedule.CRUDScheduleDto;
import org.example.schoolmanagementsystem.dtos.student.StudentListingDto;
import org.example.schoolmanagementsystem.entities.ScheduleEntity;
import org.example.schoolmanagementsystem.entities.SubjectEntity;
import org.example.schoolmanagementsystem.entities.administration.StudentEntity;
import org.example.schoolmanagementsystem.entities.administration.TeacherEntity;
import org.example.schoolmanagementsystem.mappers.ScheduleMapper;
import org.example.schoolmanagementsystem.mappers.StudentMapper;
import org.example.schoolmanagementsystem.repositories.ScheduleRepository;
import org.example.schoolmanagementsystem.repositories.StudentRepository;
import org.example.schoolmanagementsystem.repositories.SubjectRepository;
import org.example.schoolmanagementsystem.repositories.TeacherRepository;
import org.example.schoolmanagementsystem.services.interfaces.ScheduleService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final ScheduleMapper scheduleMapper;
    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;
    private final SubjectRepository subjectRepository;
    private final TeacherRepository teacherRepository;

    @Override
    public CRUDScheduleDto add(CRUDScheduleDto dto) {
        boolean isConflict = scheduleRepository.existsByClassNumberAndDayOfWeekAndStartTimeLessThanEqualAndEndTimeGreaterThanEqual(dto.getClassNumber(), dto.getDayOfWeek(), dto.getEndTime(), dto.getStartTime());

        if (isConflict) {
            throw new IllegalArgumentException("Schedule conflict: This time slot is already taken for the class.");
        }

        SubjectEntity subject = subjectRepository.findById(dto.getSubjectId()).orElseThrow(() -> new EntityNotFoundException("Subject not found with id " + dto.getSubjectId()));

        TeacherEntity teacher = teacherRepository.findById(dto.getTeacherId()).orElseThrow(() -> new EntityNotFoundException("Teacher not found with id " + dto.getTeacherId()));

        var scheduleEntity = scheduleMapper.toEntity(dto);
        scheduleEntity.setSubject(subject);
        scheduleEntity.setTeacher(teacher);

        var saved = scheduleRepository.save(scheduleEntity);
        return scheduleMapper.toDto(saved);
    }

    @Override
    public List<CRUDScheduleDto> findAll() {
        List<ScheduleEntity> entities = scheduleRepository.findAll();
        return entities.stream().map(scheduleMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public CRUDScheduleDto findById(Long id) {
        ScheduleEntity entity = scheduleRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Schedule not found with id: " + id));
        return scheduleMapper.toDto(entity);
    }

    @Override
    public CRUDScheduleDto modify(Long id, CRUDScheduleDto dto) {
        ScheduleEntity schedule = scheduleRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Schedule not found with id " + id));

        boolean isConflict = scheduleRepository.existsByClassNumberAndDayOfWeekAndStartTimeLessThanEqualAndEndTimeGreaterThanEqual(dto.getClassNumber(), dto.getDayOfWeek(), dto.getEndTime(), dto.getStartTime());

        if (isConflict) {
            throw new IllegalArgumentException("Schedule conflict: This time slot is already taken for the class.");
        }

        SubjectEntity subject = subjectRepository.findById(dto.getSubjectId()).orElseThrow(() -> new EntityNotFoundException("Subject not found with id " + dto.getSubjectId()));

        TeacherEntity teacher = teacherRepository.findById(dto.getTeacherId()).orElseThrow(() -> new EntityNotFoundException("Teacher not found with id " + dto.getTeacherId()));

        schedule.setClassNumber(dto.getClassNumber());
        schedule.setDayOfWeek(dto.getDayOfWeek());
        schedule.setStartTime(dto.getStartTime());
        schedule.setEndTime(dto.getEndTime());
        schedule.setSubject(subject);
        schedule.setTeacher(teacher);

        var updated = scheduleRepository.save(schedule);
        return scheduleMapper.toDto(updated);
    }

    @Override
    public void removeById(Long id) {
        if (!scheduleRepository.existsById(id)) {
            throw new EntityNotFoundException("Schedule not found with id: " + id);
        }
        scheduleRepository.deleteById(id);
    }

    @Override
    public List<CRUDScheduleDto> getScheduleByClass(int classNumber) {
        List<ScheduleEntity> schedules = scheduleRepository.findByClassNumber(classNumber);
        return schedules.stream().map(scheduleMapper::toDto).collect(Collectors.toList());
    }

    @Override
    public List<StudentListingDto> getStudentsByClass(Integer classNumber) {
        List<StudentEntity> students = studentRepository.findByClassNumber(classNumber);
        return students.stream().map(studentMapper::toListingDto).collect(Collectors.toList());
    }


}
