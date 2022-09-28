CREATE TABLE IF NOT EXISTS employees (
    id SERIAL PRIMARY KEY,
    login INT UNIQUE,
    name VARCHAR(100),
    salary NUMERIC(10,2) NOT NULL
);
--INSERT INTO employees (login, name, salary) VALUES (5, 'Canada', 500.1);