ALTER TABLE attendance
    ADD present BOOLEAN;

ALTER TABLE attendance
    ALTER COLUMN present SET NOT NULL;

ALTER TABLE attendance
DROP
COLUMN status;