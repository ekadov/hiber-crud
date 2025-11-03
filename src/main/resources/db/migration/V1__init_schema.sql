CREATE TABLE IF NOT EXISTS public.labels
(
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    name character varying(255) COLLATE pg_catalog."default" NOT NULL,
    post_id bigint,
    CONSTRAINT pk_1 PRIMARY KEY (id),
    CONSTRAINT pid_fk FOREIGN KEY (post_id)
        REFERENCES public.posts (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        DEFERRABLE
        NOT VALID
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.labels
    OWNER to postgres;
-- Index: fki_pid_fk

-- DROP INDEX IF EXISTS public.fki_pid_fk;

CREATE INDEX IF NOT EXISTS fki_pid_fk
    ON public.labels USING btree
    (post_id ASC NULLS LAST)
    WITH (fillfactor=100, deduplicate_items=True)
    TABLESPACE pg_default;

CREATE TABLE IF NOT EXISTS public.posts
(
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    content character varying(255) COLLATE pg_catalog."default" NOT NULL,
    created timestamp without time zone NOT NULL,
    updated timestamp without time zone,
    writer_id bigint,
    status character varying(255) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT pk PRIMARY KEY (id),
    CONSTRAINT writer_id_fk FOREIGN KEY (writer_id)
        REFERENCES public.writers (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        DEFERRABLE
        NOT VALID
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.posts
    OWNER to postgres;
-- Index: fki_writer_id_fk

-- DROP INDEX IF EXISTS public.fki_writer_id_fk;

CREATE INDEX IF NOT EXISTS fki_writer_id_fk
    ON public.posts USING btree
    (writer_id ASC NULLS LAST)
    WITH (fillfactor=100, deduplicate_items=True)
    TABLESPACE pg_default;

CREATE TABLE IF NOT EXISTS public.writers
(
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    first_name character varying(255) COLLATE pg_catalog."default" NOT NULL,
    last_name character varying(255) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT id_pk PRIMARY KEY (id)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.writers
    OWNER to postgres;