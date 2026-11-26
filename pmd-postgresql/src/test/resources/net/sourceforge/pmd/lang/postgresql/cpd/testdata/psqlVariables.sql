\set tbl accounts
SELECT * FROM :tbl WHERE n = :'name' AND m = :"quoted" AND :{?flag} AND x::int = 1;
SELECT arr[1:2], a := b;
