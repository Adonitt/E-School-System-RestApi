package org.example.schoolmanagementsystem.services.interfaces;

import org.example.schoolmanagementsystem.dtos.grade.CRDGradeDto;
import org.example.schoolmanagementsystem.dtos.grade.UpdateGradeDto;
import org.example.schoolmanagementsystem.dtos.subject.SubjectDto;
import org.example.schoolmanagementsystem.enums.SemesterEnum;
import org.example.schoolmanagementsystem.services.base_services.*;

import java.util.List;
import java.util.Map;

public interface GradeService extends
        Addable<CRDGradeDto>,
        Modifiable<UpdateGradeDto, Long>,
        Removable<Long>,
        FindAll<CRDGradeDto>,
        FindById<CRDGradeDto, Long> {

    Map<String, List<CRDGradeDto>> groupBySubject();

    Map<String, List<CRDGradeDto>> groupByAcademicYear();

    Map<String, List<CRDGradeDto>> groupBySemester();

    Map<String, List<CRDGradeDto>> groupByStudent();

    Map<String, List<CRDGradeDto>> groupByTeacher();

}
