-- SQL configs
SET SQL_MODE ='IGNORE_SPACE,ERROR_FOR_DIVISION_BY_ZERO,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION';

-- create database and use it
CREATE DATABASE IF NOT EXISTS ecommjava;
USE ecommjava;

-- create the category table
CREATE TABLE IF NOT EXISTS CATEGORY (
    category_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255)
);

-- insert default categories
INSERT INTO CATEGORY (name) VALUES
    ('Fruits'),
    ('Vegetables'),
    ('Meat'),
    ('Fish'),
    ('Dairy'),
    ('Bakery'),
    ('Drinks'),
    ('Sweets'),
    ('Other');

-- create the customer table
CREATE TABLE IF NOT EXISTS CUSTOMER (
    id INT AUTO_INCREMENT PRIMARY KEY,
    address VARCHAR(255),
    email VARCHAR(255),
    password VARCHAR(255),
    role VARCHAR(255),
    username VARCHAR(255)
);

-- insert default customers
INSERT INTO CUSTOMER (address, email, password, role, username) VALUES
    ('123, Albany Street', 'admin@nyan.cat', '123', 'ROLE_ADMIN', 'admin'),
    ('765, 5th Avenue', 'lisa@gmail.com', '765', 'ROLE_NORMAL', 'lisa');

-- create the product table
CREATE TABLE IF NOT EXISTS PRODUCT (
    product_id INT AUTO_INCREMENT PRIMARY KEY,
    description VARCHAR(255),
    image VARCHAR(255),
    name VARCHAR(255),
    price INT,
    quantity INT,
    weight INT,
    category_id INT,
    customer_id INT
);

-- insert default products
INSERT INTO PRODUCT (description, image, name, price, quantity, weight, category_id) VALUES
    ('Fresh and juicy', 'https://freepngimg.com/save/9557-apple-fruit-transparent/744x744', 'Apple', 3, 40, 76, 1),
    ('Woops! There goes the eggs...', 'https://www.nicepng.com/png/full/813-8132637_poiata-bunicii-cracked-egg.png', 'Cracked Eggs', 1, 90, 43, 9);

-- create indexes
CREATE INDEX FK7u438kvwr308xcwr4wbx36uiw ON PRODUCT (category_id);
CREATE INDEX FKt23apo8r9s2hse1dkt95ig0w5 ON PRODUCT (customer_id);
