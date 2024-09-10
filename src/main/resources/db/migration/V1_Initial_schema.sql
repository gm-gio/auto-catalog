CREATE SCHEMA IF NOT EXISTS auto_catalog;

CREATE TABLE IF NOT EXISTS auto_catalog.manufacturers
(
    manufacturer_id BIGSERIAL PRIMARY KEY,
    name            VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS auto_catalog.models
(
    model_id        BIGSERIAL PRIMARY KEY,
    name            VARCHAR(255) NOT NULL,
    manufacturer_id BIGINT,
    category        VARCHAR(255),
    FOREIGN KEY (manufacturer_id) REFERENCES auto_catalog.manufacturers (manufacturer_id)
);

CREATE TABLE IF NOT EXISTS auto_catalog.categories
(
    category_id   BIGSERIAL PRIMARY KEY,
    category_name VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS auto_catalog.cars
(
    car_id          BIGSERIAL PRIMARY KEY,
    year            INT,
    model_id        BIGINT,
    manufacturer_id BIGINT,
    FOREIGN KEY (model_id) REFERENCES auto_catalog.models (model_id),
    FOREIGN KEY (manufacturer_id) REFERENCES auto_catalog.manufacturers (manufacturer_id)
);

CREATE TABLE IF NOT EXISTS auto_catalog.cars_categories
(
    car_id  BIGINT NOT NULL,
    category_id BIGINT NOT NULL,
    PRIMARY KEY (car_id, category_id),
    FOREIGN KEY (car_id) REFERENCES auto_catalog.cars (car_id) ON DELETE CASCADE,
    FOREIGN KEY (category_id) REFERENCES auto_catalog.categories (category_id) ON DELETE CASCADE
);
