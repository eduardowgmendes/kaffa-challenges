-- SEQUENCE: public.tasks_id_seq

-- DROP SEQUENCE IF EXISTS public.tasks_id_seq;

CREATE SEQUENCE IF NOT EXISTS public.tasks_id_seq
    INCREMENT 1
    START 1
    MINVALUE 1
    MAXVALUE 9223372036854775807
    CACHE 1;

ALTER SEQUENCE public.tasks_id_seq
    OWNER TO postgres;

-- Table: public.tasks

-- DROP TABLE IF EXISTS public.tasks;

CREATE TABLE IF NOT EXISTS public.tasks
(
    id BIGINT NOT NULL DEFAULT nextval('tasks_id_seq'::regclass),
    title CHARACTER VARYING(128) COLLATE pg_catalog."default" NOT NULL,
    description TEXT COLLATE pg_catalog."default",
    status CHARACTER VARYING(64) COLLATE pg_catalog."default" NOT NULL,
    is_done BOOLEAN,
    is_completed BOOLEAN,
    is_erased BOOLEAN,
    completed_at CHARACTER VARYING(255) COLLATE pg_catalog."default",
    created_at CHARACTER VARYING(255) COLLATE pg_catalog."default",
    done_at CHARACTER VARYING(255) COLLATE pg_catalog."default",
    erased_at CHARACTER VARYING(255) COLLATE pg_catalog."default",
    updated_at CHARACTER VARYING(255) COLLATE pg_catalog."default",
    CONSTRAINT PK_tasks PRIMARY KEY (id)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.tasks
    OWNER to postgres;

ALTER SEQUENCE public.tasks_id_seq
    OWNED BY public.tasks.id;

    -- Table: public.tags

    -- DROP TABLE IF EXISTS public.tags;

    CREATE TABLE IF NOT EXISTS public.tags
    (
        task_id BIGINT NOT NULL,
        tag CHARACTER VARYING(255) COLLATE pg_catalog."default" NOT NULL,
        CONSTRAINT FK_tags_tasks FOREIGN KEY (task_id)
            REFERENCES public.tasks (id) MATCH SIMPLE
            ON UPDATE NO ACTION
            ON DELETE NO ACTION
    )

    TABLESPACE pg_default;

    ALTER TABLE IF EXISTS public.tags
        OWNER to postgres;