--liquibase formatted sql
--changeset rafaelzielinski:1

CREATE TABLE _user
(
    id        BIGINT PRIMARY KEY,
    firstname varchar(255) not null,
    lastname  varchar(255) not null,
    email     varchar(255) not null,
    password  varchar(255) not null,
    role      varchar(255) not null,
    constraint student_email_unique unique (email)
);

--liquibase formatted sql
--changeset rafaelzielinski:2
CREATE SEQUENCE IF NOT EXISTS user_sequence
    AS integer
    INCREMENT BY 1
    START 1
    OWNED BY _user.id;




