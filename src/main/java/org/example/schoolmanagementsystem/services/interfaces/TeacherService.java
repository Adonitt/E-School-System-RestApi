package org.example.schoolmanagementsystem.services.interfaces;

import org.example.schoolmanagementsystem.dtos.teacher.*;
import org.example.schoolmanagementsystem.services.base_services.*;

public interface TeacherService extends
        Addable<CreateTeacherDto>,
        Modifiable<UpdateTeacherDto, Long>,
        Removable<Long>,
        FindAll<TeacherListingDto>,
        FindById<TeacherDetailsDto, Long> {

}
