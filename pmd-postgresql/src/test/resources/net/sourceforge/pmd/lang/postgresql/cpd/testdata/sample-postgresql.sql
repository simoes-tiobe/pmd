-- Sample PostgreSQL script
CREATE TABLE accounts (
    id          bigserial PRIMARY KEY,
    owner_name  text NOT NULL,
    balance     numeric(12, 2) DEFAULT 0
);

CREATE OR REPLACE FUNCTION transfer(src bigint, dst bigint, amount numeric)
RETURNS void
LANGUAGE plpgsql
AS $$
DECLARE
    current_balance numeric;
BEGIN
    SELECT balance INTO current_balance FROM accounts WHERE id = src FOR UPDATE;
    IF current_balance < amount THEN
        RAISE EXCEPTION 'insufficient funds: % < %', current_balance, amount;
    END IF;
    UPDATE accounts SET balance = balance - amount WHERE id = src;
    UPDATE accounts SET balance = balance + amount WHERE id = dst;
END;
$$;

SELECT payload->>'name', tags @> ARRAY['a']::text[] FROM events WHERE id = $1;
