-- liquibase formatted sql

-- changeset senty:1740048776995-1
-- comment: Создание таблицы времени прибытия/отбытия
CREATE TABLE arrival_time
(
    id        BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
    check_in  time WITHOUT TIME ZONE                  NOT NULL,
    check_out time WITHOUT TIME ZONE,
    CONSTRAINT pk_arrival_time PRIMARY KEY (id)
);

