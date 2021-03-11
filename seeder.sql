USE echo_vault_db;

SELECT * FROM links;

INSERT INTO users (email, first_name, image, last_name, password, username)
VALUES ('bill@bill', 'bill', '/img/echovaultlogo.png', 'bill', '$2a$10$QO00htdOA1P8nU30brQOL.NlL7QXMMva3B2ud7IsVSFzJZbhzGD4O', 'bill')


INSERT INTO echoes (background_image, birth_date, created_at, death_date, first_name, last_name, profile_image, resting_place, summary, user_id)
VALUES ('/img/echovaultlogo.png', 'September 2020', '2020-09-21 09:00:00', 'April 2021', 'Jupiter', 'Cohort', '/img/echovaultlogo.png', 'San Antonio, TX', 'We started at around 30 students and narrowed down to 24. We have enjoyed this journey and anxiously await what is to come in our next chapter', 1)

INSERT INTO images (path, echo_id)
VALUES ('/img/echovaultlogo.png', 1);

INSERT INTO links (url, echo_id, name)
VALUES ('https://gofundme.com', 1, 'GoFundMe');


UPDATE echoes
SET profile_image = '/img/echovaultlogo.png'
WHERE id = 1;