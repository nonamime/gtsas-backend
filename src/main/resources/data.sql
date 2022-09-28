CREATE TABLE IF NOT EXISTS users (
    id SERIAL PRIMARY KEY,
    login INT UNIQUE,
    name VARCHAR(100),
    salary NUMERIC(10,2) NOT NULL
);