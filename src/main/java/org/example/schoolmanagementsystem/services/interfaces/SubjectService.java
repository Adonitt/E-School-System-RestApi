package org.example.schoolmanagementsystem.services.interfaces;

import org.example.schoolmanagementsystem.dtos.subject.CreateSubjectDto;
import org.example.schoolmanagementsystem.dtos.subject.SubjectDto;
import org.example.schoolmanagementsystem.dtos.subject.UpdateSubjectDto;
import org.example.schoolmanagementsystem.enums.SemesterEnum;
import org.example.schoolmanagementsystem.services.base_services.*;

import java.util.List;

public interface SubjectService extends
        Addable<CreateSubjectDto>,
        Modifiable<UpdateSubjectDto, Long>,
        Removable<Long>,
        FindAll<SubjectDto>,
        FindById<SubjectDto, Long> {
}
