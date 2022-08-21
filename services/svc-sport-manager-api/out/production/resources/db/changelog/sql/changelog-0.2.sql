CREATE TABLE sm.athlete
(
    id         uuid         NOT NULL,
    first_name varchar(255) NOT NULL,
    last_name  text,
    country    varchar(255),
    ftp        int4,
    version    int4,
    PRIMARY KEY (id)
);
COMMENT ON COLUMN sm.athlete.id IS 'Primary key';
CREATE TABLE sm.workout
(
    id         uuid NOT NULL,
    athlete_id uuid NOT NULL,
    version    int4,
    PRIMARY KEY (id)
);
CREATE TABLE sm.workout_type
(
    id      uuid         NOT NULL,
    code    varchar(255) NOT NULL UNIQUE,
    name    text,
    version int4,
    PRIMARY KEY (id)
);
CREATE TABLE sm.platform
(
    id       uuid         NOT NULL,
    code     varchar(255) NOT NULL UNIQUE,
    api_meta jsonb,
    name     text,
    version  int4,
    PRIMARY KEY (id)
);
CREATE TABLE sm.athlete_platform
(
    athlete_id            uuid  NOT NULL,
    platform_id           uuid  NOT NULL,
    athlete_platform_meta jsonb NOT NULL UNIQUE,
    version               int4,
    PRIMARY KEY (athlete_id,
                 platform_id)
);
CREATE TABLE sm.workout_workout_type
(
    workout_id      uuid NOT NULL,
    workout_type_id uuid NOT NULL,
    version         int4,
    PRIMARY KEY (workout_id,
                 workout_type_id)
);
CREATE TABLE sm.workout_event
(
    id            uuid                     NOT NULL,
    workout_id    uuid                     NOT NULL,
    dt            timestamp with time zone NOT NULL,
    version       int4,
    event_type_id uuid                     NOT NULL
);
CREATE TABLE sm.event_type
(
    id      uuid         NOT NULL,
    code    varchar(255) NOT NULL,
    name    text         NOT NULL,
    version int4,
    PRIMARY KEY (id)
);
CREATE TABLE sm.athlete_followers
(
    athlete_id          uuid NOT NULL,
    athlete_follower_id uuid NOT NULL,
    version             int4,
    PRIMARY KEY (athlete_id,
                 athlete_follower_id)
);
CREATE UNIQUE INDEX workout_event_id_dt_idx
    ON sm.workout_event (id, dt);
ALTER TABLE sm.workout
    ADD CONSTRAINT FKworkout377975 FOREIGN KEY (athlete_id) REFERENCES sm.athlete (id);
ALTER TABLE sm.athlete_platform
    ADD CONSTRAINT FKathlete_pl195196 FOREIGN KEY (athlete_id) REFERENCES sm.athlete (id);
ALTER TABLE sm.athlete_platform
    ADD CONSTRAINT FKathlete_pl727100 FOREIGN KEY (platform_id) REFERENCES sm.platform (id);
ALTER TABLE sm.workout_workout_type
    ADD CONSTRAINT FKworkout_wo403076 FOREIGN KEY (workout_id) REFERENCES sm.workout (id);
ALTER TABLE sm.workout_workout_type
    ADD CONSTRAINT FKworkout_wo174711 FOREIGN KEY (workout_type_id) REFERENCES sm.workout_type (id);
ALTER TABLE sm.workout_event
    ADD CONSTRAINT FKworkout_ev118489 FOREIGN KEY (workout_id) REFERENCES sm.workout (id);
ALTER TABLE sm.workout_event
    ADD CONSTRAINT FKworkout_ev273094 FOREIGN KEY (event_type_id) REFERENCES sm.event_type (id);
ALTER TABLE sm.athlete_followers
    ADD CONSTRAINT FKathlete_fo23636 FOREIGN KEY (athlete_id) REFERENCES sm.athlete (id);
ALTER TABLE sm.athlete_followers
    ADD CONSTRAINT FKathlete_fo68288 FOREIGN KEY (athlete_follower_id) REFERENCES sm.athlete (id);
