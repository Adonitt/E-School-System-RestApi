CREATE TABLE subject_class_numbers
(
    subject_id   BIGINT NOT NULL,
    class_number INTEGER
);

ALTER TABLE subject_class_numbers
    ADD CONSTRAINT fk_subject_class_numbers_on_subject_entity FOREIGN KEY (subject_id) REFERENCES subjects (id);