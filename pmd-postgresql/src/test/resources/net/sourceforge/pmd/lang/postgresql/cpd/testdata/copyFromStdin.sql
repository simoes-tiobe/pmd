COPY public.accounts (id, owner_name) FROM stdin;
1	alice 'quoted" \N
2	bob -- not a comment /* nor this
\.

SELECT 'after copy';

\copy accounts (id) from stdin
10
\.
SELECT 'after copy command';

COPY accounts TO stdout;
SELECT 'no data here';
COPY accounts FROM stdin;
\.
SELECT 'empty';
