CREATE TABLE IF NOT EXISTS users
(
    id        BIGSERIAL PRIMARY KEY,
    firstname VARCHAR(64)  NOT NULL,
    lastname  VARCHAR(64)  NOT NULL,
    email     VARCHAR(64)  NOT NULL UNIQUE,
    password  VARCHAR(124) NOT NULL,
    tel       VARCHAR(64)  NOT NULL,
    address   VARCHAR(255) NOT NULL,
    role      VARCHAR(32)  NOT NULL,
    gender    VARCHAR(32)  NOT NULL,
    blacklist varchar(32)  NOT NULL
    );

DROP TABLE orders;

CREATE TABLE IF NOT EXISTS technic
(
    id          BIGSERIAL PRIMARY KEY,
    name        VARCHAR(64) UNIQUE NOT NULL,
    category    VARCHAR(64)        NOT NULL,
    description VARCHAR(255)       NOT NULL,
    price       INT                NOT NULL,
    amount      INT                NOT NULL,
    image       VARCHAR(124)       NOT NULL
    );

CREATE TABLE IF NOT EXISTS basket
(
    id         BIGSERIAL PRIMARY KEY,
    users_id   BIGINT REFERENCES users (id) ON DELETE CASCADE   NOT NULL,
    technic_id BIGINT REFERENCES technic (id) ON DELETE CASCADE NOT NULL,
    UNIQUE (users_id, technic_id)
    );

CREATE TABLE IF NOT EXISTS orders
(
    id                BIGSERIAL PRIMARY KEY,
    product           VARCHAR(1024)                                  NOT NULL,
    user_id           BIGINT REFERENCES users (id) ON DELETE CASCADE NOT NULL,
    date_registration DATE                                           NOT NULL,
    date_close        DATE                                           NOT NULL,
    status            VARCHAR(32)                                    NOT NULL,
    total             INT                                            NOT NULL
    );