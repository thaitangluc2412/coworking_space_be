SET FOREIGN_KEY_CHECKS = 0;

CREATE TABLE `role`
(
    role_id     VARCHAR(15),
    role_name   VARCHAR(255) NOT NULL,
    time_create DATETIME DEFAULT CURRENT_TIMESTAMP,
    time_update DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT PRIMARY KEY (role_id)
);

CREATE TABLE `customer`
(
    customer_id   VARCHAR(15),
    customer_name VARCHAR(255) NOT NULL,
    email         VARCHAR(255) NOT NULL,
    password      VARCHAR(255) NOT NULL,
    phone_number  VARCHAR(255) NOT NULL,
    role_id       VARCHAR(15)  NOT NULL,
    enable        BOOLEAN,
    time_create   DATETIME DEFAULT CURRENT_TIMESTAMP,
    time_update   DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT PRIMARY KEY (customer_id),
    CONSTRAINT `fk_customer_role` FOREIGN KEY (role_id) REFERENCES `role` (role_id)
);

CREATE TABLE `price`
(
    price_id    VARCHAR(15),
    day_price   DOUBLE,
    month_price DOUBLE,
    year_price  DOUBLE,
    time_create DATETIME DEFAULT CURRENT_TIMESTAMP,
    time_update DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT PRIMARY KEY (price_id)
);

CREATE TABLE `image_storage`
(
    image_storage_id VARCHAR(15),
    time_create      DATETIME DEFAULT CURRENT_TIMESTAMP,
    time_update      DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT PRIMARY KEY (image_storage_id)
);

CREATE TABLE `image`
(
    image_id         VARCHAR(15),
    image_storage_id VARCHAR(15) NOT NULL,
    url              TEXT        NOT NULL,
    time_create      DATETIME DEFAULT CURRENT_TIMESTAMP,
    time_update      DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT PRIMARY KEY (image_id),
    CONSTRAINT `fk_image_image_storage` FOREIGN KEY (image_storage_id) REFERENCES `image_storage` (image_storage_id)
);

CREATE TABLE `utility_storage`
(
    utility_storage_id VARCHAR(15),
    time_create        DATETIME DEFAULT CURRENT_TIMESTAMP,
    time_update        DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT PRIMARY KEY (utility_storage_id)
);

CREATE TABLE `utility`
(
    utility_id         VARCHAR(15),
    utility_storage_id VARCHAR(15),
    name               VARCHAR(255) NOT NULL,
    value              VARCHAR(255) NOT NULL,
    time_create        DATETIME DEFAULT CURRENT_TIMESTAMP,
    time_update        DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT PRIMARY KEY (utility_id),
    CONSTRAINT `fk_utility_utility_storage` FOREIGN KEY (utility_storage_id) REFERENCES `utility_storage` (utility_storage_id)
);

CREATE TABLE `room_type`
(
    room_type_id     VARCHAR(15),
    image_storage_id VARCHAR(15),
    url              VARCHAR(255),
    room_type_name   VARCHAR(255),
    description      VARCHAR(255),
    time_create      DATETIME DEFAULT CURRENT_TIMESTAMP,
    time_update      DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT PRIMARY KEY (room_type_id)
);

CREATE TABLE `room`
(
    room_id            VARCHAR(15),
    room_type_id       VARCHAR(15),
    price_id           VARCHAR(15)  NOT NULL,
    room_name          VARCHAR(255) NOT NULL,
    average_rating     DOUBLE,
    address            VARCHAR(255) NOT NULL,
    province_id        INT          NOT NULL,
    district_id        INT          NOT NULL,
    ward_id            INT          NOT NULL,
    description        TEXT,
    image_storage_id   VARCHAR(15)  NOT NULL,
    utility_storage_id VARCHAR(15)  NOT NULL,
    customer_id        VARCHAR(15)  NOT NULL,
    enable             BOOLEAN,
    time_create        DATETIME DEFAULT CURRENT_TIMESTAMP,
    time_update        DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT PRIMARY KEY (room_id),
    CONSTRAINT `fk_room_room_type` FOREIGN KEY (room_type_id) REFERENCES room_type (room_type_id),
    CONSTRAINT `fk_room_price` FOREIGN KEY (price_id) REFERENCES price (price_id),
    CONSTRAINT `fk_room_image_storage` FOREIGN KEY (image_storage_id) REFERENCES image_storage (image_storage_id),
    CONSTRAINT `fk_room_customer` FOREIGN KEY (customer_id) REFERENCES customer (customer_id),
    CONSTRAINT `fk_room_utility_storage` FOREIGN KEY (utility_storage_id) REFERENCES utility_storage (utility_storage_id)
);

CREATE TABLE `review`
(
    review_id   VARCHAR(15),
    customer_id VARCHAR(15) NOT NULL,
    room_id     VARCHAR(15) NOT NULL,
    content     TEXT,
    rating      DOUBLE,
    enable      BOOLEAN,
    time_create DATETIME DEFAULT CURRENT_TIMESTAMP,
    time_update DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT PRIMARY KEY (review_id),
    CONSTRAINT `fk_review_customer` FOREIGN KEY (customer_id) REFERENCES customer (customer_id),
    CONSTRAINT `fk_review_room` FOREIGN KEY (room_id) REFERENCES room (room_id)
);

CREATE TABLE `reservation_status`
(
    reservation_status_id   VARCHAR(15),
    reservation_status_name VARCHAR(255) NOT NULL,
    time_create             DATETIME DEFAULT CURRENT_TIMESTAMP,
    time_update             DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT PRIMARY KEY (reservation_status_id)
);

CREATE TABLE `reservation`
(
    reservation_id        VARCHAR(15),
    room_id               VARCHAR(15) NOT NULL,
    customer_id           VARCHAR(15) NOT NULL,
    start_date            TIMESTAMP   NOT NULL,
    end_date              TIMESTAMP   NOT NULL,
    reservation_status_id VARCHAR(15) NOT NULL,
    total                 DOUBLE      NOT NULL,
    deposit               DOUBLE      NOT NULL,
    reviewed              BOOLEAN,
    enable                BOOLEAN,
    time_create           DATETIME DEFAULT CURRENT_TIMESTAMP,
    time_update           DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT PRIMARY KEY (reservation_id),
    CONSTRAINT `fk_reservation_room` FOREIGN KEY (room_id) REFERENCES room (room_id),
    CONSTRAINT `fk_reservation_customer` FOREIGN KEY (customer_id) REFERENCES customer (customer_id),
    CONSTRAINT `fk_reservation_reservation_status` FOREIGN KEY (reservation_status_id) REFERENCES reservation_status (reservation_status_id)
);

CREATE TABLE `behavior`
(
    behavior_id VARCHAR(15),
    time        INT,
    customer_id VARCHAR(15),
    room_id     VARCHAR(15),
    time_create DATETIME DEFAULT CURRENT_TIMESTAMP,
    time_update DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT PRIMARY KEY (behavior_id),
    CONSTRAINT `fk_behavior_customer` FOREIGN KEY (customer_id) REFERENCES customer (customer_id),
    CONSTRAINT `fk_behavior_room` FOREIGN KEY (room_id) REFERENCES room (room_id)
);
INSERT INTO `role`
VALUES ('IT04ZnPgBYSf3Qm', 'CUSTOMER', '2020-01-01 10:10:10', '2020-01-01 10:10:10'),
       ('ksWaZyhhDon1Niq', 'ADMIN', '2020-01-01 10:10:10', '2020-01-01 10:10:10');
INSERT INTO `reservation_status`
VALUES ('eT4CxDVeDhP4TsF', 'PENDING', '2020-01-01 10:10:10', '2020-01-01 10:10:10'),
       ('tngpi7zVKfwAY0N', 'APPROVED', '2020-01-01 10:10:10', '2020-01-01 10:10:10'),
       ('0KT0vOJ9BzW8w79', 'PAYING', '2020-01-01 10:10:10', '2020-01-01 10:10:10'),
       ('glG2CED3UAYrlHR', 'CANCELLED', '2020-01-01 10:10:10', '2020-01-01 10:10:10');
INSERT INTO `image_storage`(image_storage_id)
VALUES ('Gr68kjDGZaHJWan'),
       ('5BdLug6QsekC69Q'),
       ('f7xnbjkY7T934k5'),
       ('meegVeq6NcVHSQ9'),
       ('QGUPnLT27DE7SGe'),
       ('mhmZgS5Qvgxnja8'),
       ('tMdbC9ADcY9sCVx');

INSERT INTO `room_type`
VALUES ('FWFQPJnNcKjrXaU', 'Gr68kjDGZaHJWan',
        'https://res.cloudinary.com/dpom2eaqn/image/upload/v1667392068/room-type/5b441494-f08e-4e61-8693-2a3cb25ab882/image_0_xczuwl.jpg', 'Conference room', 'A conference room is a dedicated space for events such as business conference calls and meetings.

', '2020-01-01 10:10:10', '2020-01-01 10:10:10'),
       ('wTMvLkQWXwQc85u', '5BdLug6QsekC69Q',
        'https://res.cloudinary.com/dpom2eaqn/image/upload/v1667392263/room-type/4ab00f11-27e1-409b-becf-007b80f1b79f/image_1_bo69az.png', 'Breakout space',
        'This is any informal space open to everyone, including visitors and partners. This area is separate from the usual working area. Designed for quick chats and spontaneous meetings, it’s also usually an area for employees to relax, socialize or eat lunch.',
        '2020-01-01 10:10:10', '2020-01-01 10:10:10'),
       ('jv43ts59pA2mApb', 'f7xnbjkY7T934k5',
        'https://res.cloudinary.com/dpom2eaqn/image/upload/v1667392411/room-type/0497686b-57ee-4d07-88f3-6f170d2ac408/image_2_fzyjsg.jpg', 'Private office',
        'A private office is a workspace which is separated from the open office by partitions or walls. Private office space can be provided to an individual or group that are working on a special project, or undertaking work of a confidential nature.',
        '2020-01-01 10:10:10', '2020-01-01 10:10:10'),
       ('GxP3uU984FwC7Rh', 'meegVeq6NcVHSQ9',
        'https://res.cloudinary.com/dpom2eaqn/image/upload/v1667392526/room-type/b2695aee-084f-4b1d-8b98-6bb8a8e396df/image_3_zyji0f.jpg', 'Co-working space',
        'A working environment which provides office facilities for a variety of different people, from different businesses', '2020-01-01 10:10:10',
        '2020-01-01 10:10:10'),
       ('vU4NCWhTpy2Z6VC', 'QGUPnLT27DE7SGe',
        'https://res.cloudinary.com/dpom2eaqn/image/upload/v1667392634/room-type/ee40a621-bc6b-41fb-b7d1-2940ba8c2997/image_4_amgfj2.jpg', 'Event space',
        'A building used for the hosting of weddings, conferences, galas, and other similar events.', '2020-01-01 10:10:10', '2020-01-01 10:10:10'),
       ('LaQHtGeE83fur87', 'mhmZgS5Qvgxnja8',
        'https://res.cloudinary.com/dpom2eaqn/image/upload/v1667392856/room-type/65cf6708-11c6-4ff2-83b7-e2a05f8ab246/image_5_gn2c46.jpg', 'Cubicle farm',
        'A large open-plan office divided into cubicles for individual workers.', '2020-01-01 10:10:10', '2020-01-01 10:10:10'),
       ('uH2S3UZrUzAtuMk', 'tMdbC9ADcY9sCVx',
        'https://res.cloudinary.com/dpom2eaqn/image/upload/v1667393209/room-type/32712088-1298-4755-8ddb-950499a54974/image_6_pr8bcw.jpg', 'Huddle room',
        'A small space, meant for one to four people, generally informal, and most importantly private.', '2020-01-01 10:10:10', '2020-01-01 10:10:10');
INSERT INTO `customer`
VALUES ('7nX2Hy4Ygf6yoW6', 'Thai Luc', 'thaitangluc2412@gmail.com',
        '$2a$12$dFSS3PIMxvd8wgtwV2CEGuD7QkF/VnLjKH/1wNmhr4d5e.19xPLNe', '123', 'IT04ZnPgBYSf3Qm', TRUE, '2020-01-01 10:10:10', '2020-01-01 10:10:10'),
       ('CQgjk9SySBAaOl9', 'test', 'test@gmail.com',
        '$2a$12$dFSS3PIMxvd8wgtwV2CEGuD7QkF/VnLjKH/1wNmhr4d5e.19xPLNe', '123', 'IT04ZnPgBYSf3Qm', TRUE, '2020-01-01 10:10:10', '2020-01-01 10:10:10'),
       ('PyuOkHQ2e1iJFpL', 'admin', 'admin@gmail.com',
        '$2a$12$dFSS3PIMxvd8wgtwV2CEGuD7QkF/VnLjKH/1wNmhr4d5e.19xPLNe', '123', 'ksWaZyhhDon1Niq', TRUE, '2020-01-01 10:10:10', '2020-01-01 10:10:10');
SET FOREIGN_KEY_CHECKS = 1;