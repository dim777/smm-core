SELECT public.create_hypertable('sm.workout_event', 'dt');

INSERT INTO sm.platform(id, code, name, api_meta, "version")
VALUES ('022d3f7f-9344-47af-b0c5-be713f0defad', 'STRV0', 'Strava | Run and Cycling Tracking on the Social Network',
        '{
            "client_secret": "fc25169da7a733c86dd66955625c458056e16bb5",
            "access_token": "c650c194207593561c427cc71d35a70189101a7c",
            "refresh_token": "07c75b4db9e5e6020e64744e194116e12ed83479"
        }', 0);

INSERT INTO sm.platform(id, code, name, api_meta, "version")
VALUES ('54b9de72-86a8-448b-8aac-a4ac98e0d42d', 'ZWFT0', 'Zwift is virtual training for running and cycling',
        '{}', 0);

insert into sm.athlete(id, first_name, last_name, "version")
values ('619149b4-c613-4562-962f-b0dd202abc03', 'Dmitriy', 'Erokhin', 0),
       ('3d007896-03fb-40d6-a62e-4c017183e865', 'Alexander', 'Guryanov', 0),
       ('1a341b65-e3ee-4e95-96f2-2e2d0b736675', 'Dima', 'Trofimov', 0);

insert into sm.athlete_followers(athlete_id, athlete_follower_id, "version")
values ('619149b4-c613-4562-962f-b0dd202abc03', '3d007896-03fb-40d6-a62e-4c017183e865', 0),
       ('619149b4-c613-4562-962f-b0dd202abc03', '1a341b65-e3ee-4e95-96f2-2e2d0b736675', 0),
       ('3d007896-03fb-40d6-a62e-4c017183e865', '619149b4-c613-4562-962f-b0dd202abc03', 0),
       ('1a341b65-e3ee-4e95-96f2-2e2d0b736675', '619149b4-c613-4562-962f-b0dd202abc03', 0);

insert into sm.athlete_platform(athlete_id, platform_id, athlete_platform_meta, "version")
values ('619149b4-c613-4562-962f-b0dd202abc03', '022d3f7f-9344-47af-b0c5-be713f0defad', '{"id": "25076752"}', 0),
       ('3d007896-03fb-40d6-a62e-4c017183e865', '022d3f7f-9344-47af-b0c5-be713f0defad', '{"id": "7630374"}', 0),
       ('1a341b65-e3ee-4e95-96f2-2e2d0b736675', '022d3f7f-9344-47af-b0c5-be713f0defad', '{"id": "1734124"}', 0);
