ALTER TABLE subjects
    ADD semester VARCHAR(255);

ALTER TABLE subjects
DROP
COLUMN subject_id;