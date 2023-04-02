--liquibase formatted sql
--changeset rafaelzielinski:3

CREATE TABLE joke
(
    id         BIGINT PRIMARY KEY,
    category   VARCHAR(255) NOT NULL,
    type       VARCHAR(255) NOT NULL,
    joke       VARCHAR(255) NOT NULL,
    id_of_joke INTEGER      NOT NULL
);

--liquibase formatted sql
--changeset rafaelzielinski:4
CREATE SEQUENCE IF NOT EXISTS joke_sequence AS integer
    INCREMENT BY 1
    START 1
OWNED BY joke.id;


--liquibase formatted sql
--changeset rafaelzielinski:5
ALTER TABLE joke
    ADD CONSTRAINT joke_id_of_joke_unique UNIQUE (id_of_joke);
