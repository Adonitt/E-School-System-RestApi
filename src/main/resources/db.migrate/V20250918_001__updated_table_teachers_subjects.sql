    ALTER TABLE teacher_subject
    DROP
    CONSTRAINT fk_teasub_on_teacher_entity;

    ALTER TABLE teacher_subject
        ADD id BIGINT;

    ALTER TABLE teacher_subject
        ALTER COLUMN id SET NOT NULL;

    ALTER TABLE teacher_subject
        ADD CONSTRAINT fk_teasub_on_teacher_entity FOREIGN KEY (id) REFERENCES teachers (id);

    ALTER TABLE teacher_subject
    DROP
    COLUMN teacher_id;

    ALTER TABLE teachers
    DROP
    COLUMN teacher_id;