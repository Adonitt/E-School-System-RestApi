ALTER TABLE subjects
    ADD credits INTEGER;

ALTER TABLE subjects
    ADD total_hours INTEGER;

ALTER TABLE subjects
    ALTER COLUMN credits SET NOT NULL;

ALTER TABLE subjects
    ALTER COLUMN total_hours SET NOT NULL;


UPDATE subjects SET credits = 0 WHERE credits IS NULL;
UPDATE subjects SET total_hours = 0 WHERE total_hours IS NULL;


ALTER TABLE subjects ALTER COLUMN credits SET NOT NULL;
ALTER TABLE subjects ALTER COLUMN total_hours SET NOT NULL;
