SELECT $$plain 'quote' body $$;
CREATE FUNCTION f() RETURNS int AS $body$ SELECT 1 $body$ LANGUAGE sql;
DO $outer$ BEGIN EXECUTE $inner$ SELECT 1 $inner$; END $outer$;
SELECT $1, $2, $a$tag$a$;
CREATE FUNCTION g() RETURNS void AS $$
  -- a comment with a ' quote
  /* nested /* block */ comment */
  \d+ is not a psql command here
  COPY x FROM STDIN;
$$ LANGUAGE plpgsql;
CREATE FUNCTION h() RETURNS void AS $$ BEGIN NULL; END$$ LANGUAGE plpgsql;
SELECT foo$$bar;
SELECT 'after';
