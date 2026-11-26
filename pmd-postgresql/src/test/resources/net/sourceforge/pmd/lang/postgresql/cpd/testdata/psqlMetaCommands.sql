\c mydb myuser
\d+ accounts
\dt public.*
\set ON_ERROR_STOP on
\set name 'O\'Reilly'
\echo "double quoted" `date` :name
\if :flag
SELECT 1
\g
\else
\endif
\! ls -l /tmp
\C Title
\c other \\ SELECT 1;
SELECT 2 \gx
\i included.sql
\pset format aligned
\timing
