/*
===========CITIES===========
*/
insert into CITIES (CITY_ID, CITY_NAME)
VALUES (CITIES_SEQ.nextval, 'Samara');
insert into CITIES (CITY_ID, CITY_NAME)
VALUES (CITIES_SEQ.nextval, 'Moscow');
insert into CITIES (CITY_ID, CITY_NAME)
VALUES (CITIES_SEQ.nextval, 'Rostov-na-Donu');
insert into CITIES (CITY_ID, CITY_NAME)
VALUES (CITIES_SEQ.nextval, 'Sizran');
insert into CITIES (CITY_ID, CITY_NAME)
VALUES (CITIES_SEQ.nextval, 'Vladivostok');

/*
===========GYMS===========
*/
insert into GYMS (GYM_ID, GYM_NAME, GYM_WEBSITE, GYM_WEBSITE_URL, GYM_LOGO_PATH, GYM_PHONE, GYM_ADDRESS)
VALUES (GYMS_SEQ.nextval,
        'Alex Fitness',
        'alexfit.ru',
        'alexfit.ru',
        'alex-fitness.png',
        '(846) 20-95-064',
        'Moskovskoie shosse 293');
insert into GYMS (GYM_ID, GYM_NAME, GYM_WEBSITE, GYM_WEBSITE_URL, GYM_LOGO_PATH, GYM_PHONE, GYM_ADDRESS)
VALUES (GYMS_SEQ.nextval,
        'Golds Gym',
        'goldsgym.com',
        'goldsgym.com',
        'golds-gym.png',
        '(846) 20-95-064',
        'California, Muscle beach 1a');
insert into GYMS (GYM_ID, GYM_NAME, GYM_WEBSITE, GYM_WEBSITE_URL, GYM_LOGO_PATH, GYM_PHONE, GYM_ADDRESS)
VALUES (GYMS_SEQ.nextval,
        'Kin Up Wellness',
        'kinup-wellness.ru',
        'kinup-wellness.ru',
        'kinup.png',
        '(846) 20-95-064',
        'Rizhskaya 100');
insert into GYMS (GYM_ID, GYM_NAME, GYM_WEBSITE, GYM_WEBSITE_URL, GYM_LOGO_PATH, GYM_PHONE, GYM_ADDRESS)
VALUES (GYMS_SEQ.nextval,
        'X-Fit',
        'x-fit.ru',
        'x-fit.ru',
        'x-fit.png',
        '(846) 20-95-064',
        'Molodogvardeysaya 23');
insert into GYMS (GYM_ID, GYM_NAME, GYM_WEBSITE, GYM_WEBSITE_URL, GYM_LOGO_PATH, GYM_PHONE, GYM_ADDRESS)
VALUES (GYMS_SEQ.nextval,
        'Zebra',
        'zebra-fitness.ru',
        'zebra-fitness.ru',
        'zebra-fit.png',
        '(846) 20-95-064',
        'Kirovsjaya 192');

/*
===========EQUIPMENT===========
*/
insert into EQUIPMENT (EQUIPMENT_ID, EQUIPMENT_NAME, EQUIPMENT_DESCRIPTION, EQUIPMENT_IMG_PATH)
VALUES (EQUIPMENT_SEQ.nextval,
        'Personal Bike',
        'This bike helps to loose weight and also to gain muscles and keep in shape',
        'bike-personal.jpg');
insert into EQUIPMENT (EQUIPMENT_ID, EQUIPMENT_NAME, EQUIPMENT_DESCRIPTION, EQUIPMENT_IMG_PATH)
VALUES (EQUIPMENT_SEQ.nextval,
        'Quadriceps machine',
        'This machine helps you to focus on your quadricepses and to reach maximum muscle contraction peak.',
        'leg_ext_artis_h9.jpg');
insert into EQUIPMENT (EQUIPMENT_ID, EQUIPMENT_NAME, EQUIPMENT_DESCRIPTION, EQUIPMENT_IMG_PATH)
VALUES (EQUIPMENT_SEQ.nextval,
        'Multipower',
        'Here you can do different things: sit-ups, pull-ups and more. When doing sit-ups try to concentrate on the back surface of your leg',
        'multipower.jpg');
insert into EQUIPMENT (EQUIPMENT_ID, EQUIPMENT_NAME, EQUIPMENT_DESCRIPTION, EQUIPMENT_IMG_PATH)
VALUES (EQUIPMENT_SEQ.nextval,
        'Personal Bike',
        'This bike helps to loose weight and also to gain muscles and keep in shape',
        'bike-personal.jpg');

/*
===========USERS===========
*/
insert into USERS (USER_ID, USER_NAME, USER_EMAIL, USER_PASSWORD, USER_MODE)
VALUES (USERS_SEQ.nextval,
        'Kirilloid R',
        'kirill.rodichev38@gmail.com',
        'admin',
        'admin');
insert into USERS (USER_ID, USER_NAME, USER_EMAIL, USER_PASSWORD, USER_MODE)
VALUES (USERS_SEQ.nextval,
        'Daniil Koveev',
        'daniil.kov1289@gmail.com',
        'user',
        'user');
insert into USERS (USER_ID, USER_NAME, USER_EMAIL, USER_PASSWORD, USER_MODE)
VALUES (USERS_SEQ.nextval,
        'Nekit Poliansky',
        'nekit294@yandex.ru',
        'admin',
        'admin');

/*
===========BODY-GROUPS===========
*/
insert into BODY_GROUPS (B_GROUP_ID, B_GROUP_NAME)
VALUES (B_GROUP_SEQ.nextval, 'Neck');
insert into BODY_GROUPS (B_GROUP_ID, B_GROUP_NAME)
VALUES (B_GROUP_SEQ.nextval, 'Traps');
insert into BODY_GROUPS (B_GROUP_ID, B_GROUP_NAME)
VALUES (B_GROUP_SEQ.nextval, 'Shoulders');
insert into BODY_GROUPS (B_GROUP_ID, B_GROUP_NAME)
VALUES (B_GROUP_SEQ.nextval, 'Chest');
insert into BODY_GROUPS (B_GROUP_ID, B_GROUP_NAME)
VALUES (B_GROUP_SEQ.nextval, 'Biceps');
insert into BODY_GROUPS (B_GROUP_ID, B_GROUP_NAME)
VALUES (B_GROUP_SEQ.nextval, 'Forearms');
insert into BODY_GROUPS (B_GROUP_ID, B_GROUP_NAME)
VALUES (B_GROUP_SEQ.nextval, 'Abs');
insert into BODY_GROUPS (B_GROUP_ID, B_GROUP_NAME)
VALUES (B_GROUP_SEQ.nextval, 'Quadriceps');
insert into BODY_GROUPS (B_GROUP_ID, B_GROUP_NAME)
VALUES (B_GROUP_SEQ.nextval, 'Calves');
insert into BODY_GROUPS (B_GROUP_ID, B_GROUP_NAME)
VALUES (B_GROUP_SEQ.nextval, 'Hamstrings');
insert into BODY_GROUPS (B_GROUP_ID, B_GROUP_NAME)
VALUES (B_GROUP_SEQ.nextval, 'Glutes');
insert into BODY_GROUPS (B_GROUP_ID, B_GROUP_NAME)
VALUES (B_GROUP_SEQ.nextval, 'Lower Back');
insert into BODY_GROUPS (B_GROUP_ID, B_GROUP_NAME)
VALUES (B_GROUP_SEQ.nextval, 'Upper Back');
insert into BODY_GROUPS (B_GROUP_ID, B_GROUP_NAME)
VALUES (B_GROUP_SEQ.nextval, 'Triceps');

/*
===========USERS-CITIES===========
*/
insert into USERS_CITIES (USER_ID, CITY_ID)
VALUES (1, 1);
insert into USERS_CITIES (USER_ID, CITY_ID)
VALUES (2, 2);
insert into USERS_CITIES (USER_ID, CITY_ID)
VALUES (3, 5);

/*
===========GYMS-CITIES===========
*/
insert into GYMS_CITIES (CITY_ID, GYM_ID)
VALUES (1, 1);
insert into GYMS_CITIES (CITY_ID, GYM_ID)
VALUES (2, 1);
insert into GYMS_CITIES (CITY_ID, GYM_ID)
VALUES (3, 1);
insert into GYMS_CITIES (CITY_ID, GYM_ID)
VALUES (4, 3);
insert into GYMS_CITIES (CITY_ID, GYM_ID)
VALUES (5, 2);

/*
===========GYMS-EQUIPMENT===========
*/
insert into GYMS_EQUIPMENT (EQUIPMENT_ID, GYM_ID)
VALUES (1, 1);
insert into GYMS_EQUIPMENT (EQUIPMENT_ID, GYM_ID)
VALUES (2, 1);
insert into GYMS_EQUIPMENT (EQUIPMENT_ID, GYM_ID)
VALUES (3, 1);
insert into GYMS_EQUIPMENT (EQUIPMENT_ID, GYM_ID)
VALUES (2, 2);
insert into GYMS_EQUIPMENT (EQUIPMENT_ID, GYM_ID)
VALUES (3, 2);
insert into GYMS_EQUIPMENT (EQUIPMENT_ID, GYM_ID)
VALUES (3, 3);
insert into GYMS_EQUIPMENT (EQUIPMENT_ID, GYM_ID)
VALUES (1, 3);
insert into GYMS_EQUIPMENT (EQUIPMENT_ID, GYM_ID)
VALUES (1, 4);
insert into GYMS_EQUIPMENT (EQUIPMENT_ID, GYM_ID)
VALUES (2, 5);
insert into GYMS_EQUIPMENT (EQUIPMENT_ID, GYM_ID)
VALUES (1, 5);
insert into GYMS_EQUIPMENT (EQUIPMENT_ID, GYM_ID)
VALUES (3, 5);

/*
===========GYMS-USERS===========
*/
insert into USERS_GYMS (USER_ID, GYM_ID)
VALUES (1, 3);
insert into USERS_GYMS (USER_ID, GYM_ID)
VALUES (2, 4);
insert into USERS_GYMS (USER_ID, GYM_ID)
VALUES (3, 2);

/*
===========BODY-GROUP--EQUIPMENT===========
*/
insert into B_GROUPS_EQUIPMENT (B_GROUP_ID, EQUIPMENT_ID)
VALUES (8, 1);
insert into B_GROUPS_EQUIPMENT (B_GROUP_ID, EQUIPMENT_ID)
VALUES (9, 1);
insert into B_GROUPS_EQUIPMENT (B_GROUP_ID, EQUIPMENT_ID)
VALUES (10, 1);
insert into B_GROUPS_EQUIPMENT (B_GROUP_ID, EQUIPMENT_ID)
VALUES (8, 2);
insert into B_GROUPS_EQUIPMENT (B_GROUP_ID, EQUIPMENT_ID)
VALUES (8, 3);
insert into B_GROUPS_EQUIPMENT (B_GROUP_ID, EQUIPMENT_ID)
VALUES (9, 3);
insert into B_GROUPS_EQUIPMENT (B_GROUP_ID, EQUIPMENT_ID)
VALUES (10, 3);
insert into B_GROUPS_EQUIPMENT (B_GROUP_ID, EQUIPMENT_ID)
VALUES (11, 3);
insert into B_GROUPS_EQUIPMENT (B_GROUP_ID, EQUIPMENT_ID)
VALUES (12, 3);

