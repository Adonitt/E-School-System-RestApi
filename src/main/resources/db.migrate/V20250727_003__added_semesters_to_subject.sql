CREATE TABLE subject_semesters
(
    subject_id BIGINT NOT NULL,
    semester   VARCHAR(255)
);

ALTER TABLE subject_semesters
    ADD CONSTRAINT fk_subject_semesters_on_subject_entity FOREIGN KEY (subject_id) REFERENCES subjects (id);

ALTER TABLE subjects
DROP
COLUMN semester;