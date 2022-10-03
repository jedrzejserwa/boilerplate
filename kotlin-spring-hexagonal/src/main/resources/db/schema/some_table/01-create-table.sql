--liquibase formatted sql
--changeset jj:1
CREATE TABLE palindrome
(
	id           VARCHAR(256) PRIMARY KEY,
	source       VARCHAR(256) NOT NULL,
	result       VARCHAR(256) NOT NULL,
	date_created TIMESTAMP    NOT NULL
);

CREATE INDEX palindrome_source_idx ON palindrome (source);
