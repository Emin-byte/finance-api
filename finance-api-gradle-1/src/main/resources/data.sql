CREATE EXTENSION IF NOT EXISTS pgcrypto;
INSERT INTO users (id, name, balance) VALUES (gen_random_uuid(), 'Alice', 1000.00);
INSERT INTO users (id, name, balance) VALUES (gen_random_uuid(), 'Bob', 500.00);
