ALTER TABLE subjects
DROP
CONSTRAINT fk_subjects_on_subject;

ALTER TABLE subjects
DROP
COLUMN subject_id;
