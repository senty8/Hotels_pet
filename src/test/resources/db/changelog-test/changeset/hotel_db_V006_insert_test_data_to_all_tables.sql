-- liquibase formatted sql
-- changeset senty:test-data-1
INSERT INTO address (house_number, street, city, country, post_code)
VALUES (9, 'Pobediteley Avenue', 'Minsk', 'Belarus', '220004'),
       (22, 'Broadway', 'New York', 'USA', '90012'),
       (5, '5th Avenue', 'New York', 'USA', '60601');

-- liquibase formatted sql
-- changeset senty:test-data-2
INSERT INTO contacts (phone, email)
VALUES ('+375 17 309-80-00', 'doubletreeminsk.info@hilton.com'),
       ('+1-213-555-0202', 'hotel2@example.com'),
       ('+1-312-555-0303', 'hotel3@example.com');

-- liquibase formatted sql
-- changeset senty:test-data-3
INSERT INTO arrival_time (check_in, check_out)
VALUES ('14:00:00', '12:00:00'),
       ('15:00:00', '12:00:00'),
       ('13:00:00', '10:30:00');

-- liquibase formatted sql
-- changeset senty:test-data-4
INSERT INTO amenity (name)
VALUES ('Free parking'),
       ('Free WiFi'),
       ('Non-smoking rooms'),
       ('Concierge'),
       ('On-site restaurant'),
       ('Fitness center'),
       ('Pet-friendly rooms'),
       ('Room service'),
       ('Business center'),
       ('Meeting rooms'),
       ('Play Station 5');


-- liquibase formatted sql
-- changeset senty:test-data-5
INSERT INTO hotel (name, description, brand, address_id, contacts_id, arrival_time_id)
VALUES ('DoubleTree by Hilton Minsk',
        'The DoubleTree by Hilton Hotel Minsk offers 193 luxurious rooms in the Belorussian capital and stunning views of Minsk city from the hotel''s 20th floor ...',
        'Hilton', 1, 1, 1),
       ('Seaside Resort', 'A beachfront hotel with ocean view', 'Marriott', 2, 2, 2),
       ('Mountain Resort', 'A cozy lodge in the mountains', 'Marriott', 3, 3, 3);

-- liquibase formatted sql
-- changeset senty:test-data-6
INSERT INTO hotel_amenity (hotel_id, amenity_id)
VALUES (1, 1),
       (1, 2),
       (1, 3),
       (1, 4),
       (1, 5),
       (1, 6),
       (1, 7),
       (1, 8),
       (1, 9),
       (1, 10), -- Grand Hotel
       (2, 1),
       (2, 2),
       (2, 11),  -- Seaside Resort
       (3, 4),
       (3, 5),
       (3, 11);  -- Mountain Resort