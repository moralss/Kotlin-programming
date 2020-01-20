CREATE TABLE IF NOT EXISTS users(
    id serial PRIMARY KEY,
    email varchar(255) NOT NULL UNIQUE,
    first_name varchar(22) NOT NULL,
    last_name varchar(22) NOT NULL,
);

