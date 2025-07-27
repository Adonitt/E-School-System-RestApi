ALTER TABLE grades
    ADD attendance_percentage_used DOUBLE PRECISION;

ALTER TABLE subjects
    ADD subject_id BIGINT;

ALTER TABLE subjects
    ADD CONSTRAINT FK_SUBJECTS_ON_SUBJECT FOREIGN KEY (subject_id) REFERENCES subjects (id);

ALTER TABLE students
    ALTER COLUMN class_number SET NOT NULL;