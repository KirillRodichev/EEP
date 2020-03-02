DROP TABLE additional_info;
DROP TABLE cities;

DROP TABLE chest_equipment;
DROP TABLE legs_equipment;
DROP TABLE back_equipment;
DROP TABLE equipment;

DROP TABLE training_programs;
DROP TABLE gyms;
DROP TABLE users_1;

CREATE TABLE users_1
(
    user_id         NUMBER PRIMARY KEY,
    user_name       VARCHAR2(20 CHAR) NOT NULL,
    user_email      VARCHAR2(30 CHAR) NOT NULL,
    user_password   VARCHAR2(30 CHAR) NOT NULL,
    user_city       VARCHAR2(20 CHAR) NOT NULL,
    user_gym        VARCHAR2(20 CHAR) NOT NULL,
    user_addit_info NUMBER            NOT NULL,
    user_train_prog NUMBER            NOT NULL
);

CREATE TABLE additional_info
(
    info_id            NUMBER            NOT NULL PRIMARY KEY,
    user_id            NUMBER            NULL,
    gender          VARCHAR2(6)       NOT NULL,
    weight             INT               NOT NULL,
    height             INT               NOT NULL,
    pref_training_type VARCHAR2(20 CHAR) NOT NULL,
    CONSTRAINT info_user_fk
        FOREIGN KEY (user_id) REFERENCES users_1 (user_id)
);

CREATE TABLE training_programs
(
    program_id NUMBER            NOT NULL PRIMARY KEY,
    user_id    NUMBER            NULL,
    split_type VARCHAR2(10 CHAR) NOT NULL,
    first_day  VARCHAR2(20 CHAR) NOT NULL,
    second_day VARCHAR2(20 CHAR) NOT NULL,
    third_day  VARCHAR2(20 CHAR) NOT NULL,
    CONSTRAINT program_user_fk
        FOREIGN KEY (user_id) REFERENCES users_1 (user_id)
);

CREATE TABLE cities
(
    city_name varchar2(20 CHAR) NOT NULL PRIMARY KEY,
    user_id   NUMBER           NULL,
    CONSTRAINT city_user_fk
        FOREIGN KEY (user_id) REFERENCES users_1 (user_id)
);

CREATE TABLE gyms
(
    gym_name      varchar2(20 CHAR) NOT NULL PRIMARY KEY,
    user_id       NUMBER            NULL,
    gym_equipment NUMBER            NULL,
    CONSTRAINT gym_user_fk
        FOREIGN KEY (user_id) REFERENCES users_1 (user_id)
);

CREATE TABLE equipment
(
    equipment_id NUMBER NOT NULL PRIMARY KEY,
    gym_name     varchar2(20 CHAR) NULL,
    legs_equip   NUMBER NOT NULL,
    chest_equip  NUMBER NOT NULL,
    back_equip   NUMBER NOT NULL,
    CONSTRAINT equipment_gym_fk
        FOREIGN KEY (gym_name) REFERENCES gyms (gym_name)
);

CREATE TABLE legs_equipment
(
    legs_id   NUMBER NOT NULL PRIMARY KEY,
    equip_id  NUMBER NULL,
    legs_name Varchar2(30 CHAR),
    CONSTRAINT legs_equipment_fk
        FOREIGN KEY (equip_id) REFERENCES equipment (equipment_id)
);

CREATE TABLE chest_equipment
(
    chest_id   NUMBER NOT NULL PRIMARY KEY,
    equip_id   NUMBER NULL,
    chest_name VARCHAR2(30 CHAR),
    CONSTRAINT chest_equipment_fk
        FOREIGN KEY (equip_id) REFERENCES equipment (equipment_id)
);

CREATE TABLE back_equipment
(
    back_id   NUMBER NOT NULL PRIMARY KEY,
    equip_id  NUMBER NULL,
    back_name VARCHAR2(30 CHAR),
    CONSTRAINT back_equipment_fk
        FOREIGN KEY (equip_id) REFERENCES equipment (equipment_id)
);