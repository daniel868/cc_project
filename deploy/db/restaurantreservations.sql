CREATE SCHEMA restaurantreservations AUTHORIZATION postgres;

set schema restaurantreservations;

CREATE TABLE restaurantreservations.restaurant (
  id INT NOT NULL,
  name varchar(200) NOT NULL,
  phone varchar(10) NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE restaurantreservations.reservation (
  id INT NOT NULL,
  customer_id varchar NOT NULL,
  reservation_date DATE,
  reservation_time TIME,
  guest_number INT not null,
  PRIMARY KEY (id),
  FOREIGN KEY (customer_id)
      REFERENCES restaurantreservations.customer (id)
);

CREATE TABLE restaurantreservations.restaurant (
  id INT NOT NULL,
  name varchar NOT NULL,
  owner varchar not null,
  address varchar not NULL,
  available_spots INT not null,
  maximum_guest_number INT not null,
  PRIMARY KEY (id)
);

CREATE TABLE restaurantreservations.restaurant (
  id INT NOT NULL,
  name varchar NOT NULL,
  owner varchar not null,
  address varchar not NULL,
  available_spots INT not null,
  maximum_guest_number INT not null,
  PRIMARY KEY (id)
);

ALTER TABLE restaurantreservations.reservation
 ADD CONSTRAINT fk_restaurant_id FOREIGN KEY (restaurant_id) 
 REFERENCES restaurantreservations.restaurant (id);