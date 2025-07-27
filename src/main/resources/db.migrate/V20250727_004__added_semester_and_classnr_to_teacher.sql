CREATE TABLE teachers_class_numbers
(
    teachers_id   BIGINT NOT NULL,
    class_numbers INTEGER
);

CREATE TABLE teachers_semesters
(
    teachers_id BIGINT NOT NULL,
    semesters   VARCHAR(255)
);

ALTER TABLE teachers_class_numbers
    ADD CONSTRAINT fk_teachers_classnumbers_on_teacher_entity FOREIGN KEY (teachers_id) REFERENCES teachers (id);

ALTER TABLE teachers_semesters
    ADD CONSTRAINT fk_teachers_semesters_on_teacher_entity FOREIGN KEY (teachers_id) REFERENCES teachers (id);