CREATE TABLE parkings
(
    id         VARCHAR(255) NOT NULL,
    code       VARCHAR(4)   NOT NULL,
    status     VARCHAR(255) NOT NULL,
    created_at datetime NULL,
    updated_at datetime NULL,
    created_by VARCHAR(255) NULL,
    updated_by VARCHAR(255) NULL,
    CONSTRAINT pk_parkings PRIMARY KEY (id)
);

ALTER TABLE parkings
    ADD CONSTRAINT uc_parkings_code UNIQUE (code);