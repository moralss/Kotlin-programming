-- CREATE TABLE IF NOT EXISTS budgets(
--     id serial PRIMARY KEY,
--     email varchar(255) NOT NULL UNIQUE,
--     first_name varchar(22) NOT NULL,
--     last_name varchar(22) NOT NULL,
--     hashed_password varchar(225) NOT NULL,
--     contact varchar(14) NOT NULL,
--     ref_number varchar(20) NOT NULL,
--     created_at timestamp NOT NULL DEFAULT NOW() NOT NULL,
--     updated_at timestamp NOT NULL DEFAULT NOW() NOT NULL
-- );

CREATE TABLE IF NOT EXISTS budgets(
    id serial PRIMARY KEY,
    budget_name varchar(22) NOT NULL,
    budget_amount DECIMAL (4, 2) NOT NULL,
    monthly_budget_id INT REFERENCES monthly_budgets(id) NOT NULL,
    created_at timestamp NOT NULL DEFAULT NOW() NOT NULL,
    updated_at timestamp NOT NULL DEFAULT NOW() NOT NULL
);

CREATE TABLE IF NOT EXISTS monthly_budgets(
    id serial PRIMARY KEY,
    date_budget varchar(22) NOT NULL,
    month_budget DECIMAL (6, 2) NOT NULL,
    created_at timestamp NOT NULL DEFAULT NOW() NOT NULL,
    updated_at timestamp NOT NULL DEFAULT NOW() NOT NULL
);


DELETE FROM monthly_budgets;
