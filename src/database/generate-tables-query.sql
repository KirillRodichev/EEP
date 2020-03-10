BEGIN
    EXECUTE IMMEDIATE 'DROP TABLE additional_info';
EXCEPTION
    WHEN OTHERS THEN
        IF SQLCODE != -942 THEN
            RAISE;
        END IF;
END;
BEGIN
    EXECUTE IMMEDIATE 'DROP TABLE training_programs';
EXCEPTION
    WHEN OTHERS THEN
        IF SQLCODE != -942 THEN
            RAISE;
        END IF;
END;
BEGIN
    EXECUTE IMMEDIATE 'DROP TABLE users_gyms';
EXCEPTION
    WHEN OTHERS THEN
        IF SQLCODE != -942 THEN
            RAISE;
        END IF;
END;
BEGIN
    EXECUTE IMMEDIATE 'DROP TABLE users_cities';
EXCEPTION
    WHEN OTHERS THEN
        IF SQLCODE != -942 THEN
            RAISE;
        END IF;
END;
BEGIN
    EXECUTE IMMEDIATE 'DROP TABLE gyms_equipment';
EXCEPTION
    WHEN OTHERS THEN
        IF SQLCODE != -942 THEN
            RAISE;
        END IF;
END;
BEGIN
    EXECUTE IMMEDIATE 'DROP TABLE gyms_cities';
EXCEPTION
    WHEN OTHERS THEN
        IF SQLCODE != -942 THEN
            RAISE;
        END IF;
END;
BEGIN
    EXECUTE IMMEDIATE 'DROP TABLE b_groups_equipment';
EXCEPTION
    WHEN OTHERS THEN
        IF SQLCODE != -942 THEN
            RAISE;
        END IF;
END;
BEGIN
    EXECUTE IMMEDIATE 'DROP TABLE cities';
EXCEPTION
    WHEN OTHERS THEN
        IF SQLCODE != -942 THEN
            RAISE;
        END IF;
END;
BEGIN
    EXECUTE IMMEDIATE 'DROP TABLE equipment';
EXCEPTION
    WHEN OTHERS THEN
        IF SQLCODE != -942 THEN
            RAISE;
        END IF;
END;
BEGIN
    EXECUTE IMMEDIATE 'DROP TABLE gyms';
EXCEPTION
    WHEN OTHERS THEN
        IF SQLCODE != -942 THEN
            RAISE;
        END IF;
END;
BEGIN
    EXECUTE IMMEDIATE 'DROP TABLE body_groups';
EXCEPTION
    WHEN OTHERS THEN
        IF SQLCODE != -942 THEN
            RAISE;
        END IF;
END;
BEGIN
    EXECUTE IMMEDIATE 'DROP TABLE users';
EXCEPTION
    WHEN OTHERS THEN
        IF SQLCODE != -942 THEN
            RAISE;
        END IF;
END;

DROP SEQUENCE users_seq;
DROP SEQUENCE cities_seq;
DROP SEQUENCE gyms_seq;
DROP SEQUENCE equipment_seq;
DROP SEQUENCE b_group_seq;

CREATE SEQUENCE users_seq
    START WITH 1
    CACHE 10;

CREATE SEQUENCE cities_seq
    START WITH 1
    CACHE 10;

CREATE SEQUENCE gyms_seq
    START WITH 1
    CACHE 10;

CREATE SEQUENCE equipment_seq
    START WITH 1
    CACHE 10;

CREATE SEQUENCE b_group_seq
    START WITH 1
    CACHE 10;

CREATE TABLE users
(
    user_id         NUMBER       PRIMARY KEY,
    user_name       VARCHAR2(20) NOT NULL,
    user_email      VARCHAR2(30) NOT NULL,
    user_password   VARCHAR2(20) NOT NULL,
    user_mode       VARCHAR2(5)  NOT NULL
);
CREATE TABLE additional_info
(
    user_id       NUMBER            PRIMARY KEY,
    gender        VARCHAR2(6)       NOT NULL,
    weight        INT               NOT NULL,
    height        INT               NOT NULL,
    training_type VARCHAR2(20)      NOT NULL,
    CONSTRAINT info_user_fk
        FOREIGN KEY (user_id) REFERENCES users (user_id)
);
CREATE TABLE training_programs
(
    user_id    NUMBER       PRIMARY KEY,
    split_type VARCHAR2(10) NOT NULL,
    first_day  VARCHAR2(20) NOT NULL,
    second_day VARCHAR2(20) NOT NULL,
    third_day  VARCHAR2(20) NOT NULL,
    CONSTRAINT program_user_fk
        FOREIGN KEY (user_id) REFERENCES users (user_id)
);
CREATE TABLE cities
(
    city_id     NUMBER          PRIMARY KEY,
    city_name   VARCHAR2(20)    NOT NULL
);

CREATE TABLE gyms
(
    gym_id          NUMBER          PRIMARY KEY,
    gym_name        VARCHAR2(20)    NOT NULL,
    gym_website     VARCHAR2(30),
    gym_website_url VARCHAR2(20),
    gym_logo_path   VARCHAR2(20),
    gym_phone       VARCHAR2(20),
    gym_address     VARCHAR2(40)
);

CREATE TABLE equipment
(
    equipment_id            NUMBER       PRIMARY KEY,
    equipment_name          VARCHAR2(20) NOT NULL,
    equipment_description   VARCHAR2(200),
    equipment_img_path      VARCHAR2(20)
);

CREATE TABLE body_groups
(
    b_group_id      NUMBER          PRIMARY KEY,
    b_group_name    VARCHAR2(20)    NOT  NULL
);
CREATE TABLE b_groups_equipment
(
    b_group_id      NUMBER NOT NULL,
    equipment_id    NUMBER NOT NULL,
    CONSTRAINT b_group_fk
        FOREIGN KEY (b_group_id)
            REFERENCES body_groups (b_group_id),
    CONSTRAINT equip_fk__
        FOREIGN KEY (equipment_id)
            REFERENCES equipment (equipment_id)
);

CREATE TABLE users_cities
(
    user_id NUMBER NOT NULL,
    city_id NUMBER NOT NULL,
    CONSTRAINT user_fk
        FOREIGN KEY (user_id)
            REFERENCES users (user_id),
    CONSTRAINT city_fk
        FOREIGN KEY (city_id)
            REFERENCES cities (city_id)
);

CREATE TABLE users_gyms
(
    user_id NUMBER NOT NULL,
    gym_id NUMBER NOT NULL,
    CONSTRAINT user_fk_
        FOREIGN KEY (user_id)
            REFERENCES users (user_id),
    CONSTRAINT gym_fk_
        FOREIGN KEY (gym_id)
            REFERENCES gyms (gym_id)
);

CREATE TABLE gyms_equipment
(
    equipment_id NUMBER NOT NULL,
    gym_id NUMBER NOT NULL,
    CONSTRAINT gym_fk
        FOREIGN KEY (gym_id)
            REFERENCES gyms (gym_id),
    CONSTRAINT equipment_fk__
        FOREIGN KEY (equipment_id)
            REFERENCES equipment (equipment_id)
);

CREATE TABLE gyms_cities
(
    city_id NUMBER NOT NULL,
    gym_id NUMBER NOT NULL,
    CONSTRAINT gym_fk__
        FOREIGN KEY (gym_id)
            REFERENCES gyms (gym_id),
    CONSTRAINT city_fk_
        FOREIGN KEY (city_id)
            REFERENCES cities (city_id)
);

commit;
