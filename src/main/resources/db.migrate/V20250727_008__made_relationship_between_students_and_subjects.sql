CREATE TABLE student_subject
(
    student_id BIGINT NOT NULL,
    subject_id BIGINT NOT NULL
);

ALTER TABLE student_subject
    ADD CONSTRAINT fk_stusub_on_student_entity FOREIGN KEY (student_id) REFERENCES students (id);

ALTER TABLE student_subject
    ADD CONSTRAINT fk_stusub_on_subject_entity FOREIGN KEY (subject_id) REFERENCES subjects (id);