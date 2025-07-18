ALTER TABLE students
    ADD class_number INTEGER;

ALTER TABLE students
    ALTER COLUMN class_number SET NOT NULL;

ALTER TABLE students ALTER COLUMN class_number SET DEFAULT 0;

UPDATE students SET class_number = 0 WHERE class_number IS NULL;
