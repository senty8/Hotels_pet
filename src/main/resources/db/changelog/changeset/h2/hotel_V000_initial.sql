CREATE TABLE IF NOT EXISTS address (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         house_number INT NOT NULL,
                         street VARCHAR(128) NOT NULL,
                         city VARCHAR(64) NOT NULL,
                         country VARCHAR(64) NOT NULL,
                         post_code VARCHAR(32) NOT NULL
);

CREATE TABLE IF NOT EXISTS contacts (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          phone VARCHAR(32) NOT NULL,
                          email VARCHAR(64) NOT NULL
);

CREATE TABLE IF NOT EXISTS arrival_time (
                              id BIGINT AUTO_INCREMENT PRIMARY KEY,
                              check_in VARCHAR(32) NOT NULL,
                              check_out VARCHAR(32)
);

CREATE TABLE IF NOT EXISTS amenity (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         name VARCHAR(64) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS hotel (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       name VARCHAR(128) NOT NULL,
                       description VARCHAR(4096),
                       brand VARCHAR(128) NOT NULL,
                       address_id BIGINT NOT NULL,
                       contacts_id BIGINT NOT NULL,
                       arrival_time_id BIGINT NOT NULL,
                       FOREIGN KEY (address_id) REFERENCES address(id) ON DELETE CASCADE,
                       FOREIGN KEY (contacts_id) REFERENCES contacts(id) ON DELETE CASCADE,
                       FOREIGN KEY (arrival_time_id) REFERENCES arrival_time(id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS hotel_amenity (
                               hotel_id BIGINT NOT NULL,
                               amenity_id BIGINT NOT NULL,
                               PRIMARY KEY (hotel_id, amenity_id),
                               FOREIGN KEY (hotel_id) REFERENCES hotel(id) ON DELETE CASCADE,
                               FOREIGN KEY (amenity_id) REFERENCES amenity(id) ON DELETE CASCADE
);