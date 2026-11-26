---
title: PostgreSQL support
permalink: pmd_languages_postgresql.html
last_updated: November 2026 (7.30.0)
tags: [languages, CpdCapableLanguage]
summary: "PostgreSQL-specific features and guidance"
---

> [PostgreSQL](https://www.postgresql.org/docs/current/sql.html) is an open-source relational database.
> Its SQL dialect and its procedural language PL/pgSQL are used in scripts, migrations and database dumps.

{% include language_info.html name='PostgreSQL' id='postgresql' implementation='postgresql::lang.postgresql.PostgreSQLLanguageModule' supports_cpd=true since='7.30.0' %}

PostgreSQL is supported by CPD only. There are no PMD rules for it.

The file extensions are `.sql`, `.pgsql` and `.psql`. The extension `.sql` is also used by the PL/SQL and T-SQL
languages, so always select the language explicitly: `pmd cpd --language postgresql`.

## What the lexer understands

* Keywords are case-insensitive and unquoted identifiers are folded to lower case, as PostgreSQL does.
  Quoted identifiers (`"Foo"`) and string literals keep their case.
* Block comments can be nested (`/* outer /* inner */ outer */`).
* All string forms: `'...'`, `E'...'` (backslash escapes), `U&'...'`, `B'...'`, `X'...'` and `N'...'`.
* Dollar-quoted strings (`$$ ... $$` and `$tag$ ... $tag$`) are not one single token. Their content is tokenized
  as SQL, so that CPD finds duplicated code inside PL/pgSQL function bodies. Only the same tag closes the string.
  A body written in another language (PL/Python, ...) is tokenized on a best-effort basis: an unbalanced quote
  in such a body can make the rest of the file be tokenized wrongly.

### psql scripts

Scripts that are meant for the `psql` client, such as `pg_dump` output, are supported:

* Backslash meta-commands such as `\c`, `\d+`, `\set`, `\i`, `\if` or `\!` are tokenized as a command followed by its
  arguments, up to the end of the line or to the next `\\`. Quoted arguments, `` `shell commands` `` and `:variable`
  references are recognized. The case of commands and arguments is kept, because `\C` and `\c` are different commands.
* Backslash commands are only recognized outside of strings, comments and dollar-quoted strings. Like in `psql`,
  they can also follow SQL on the same line (`SELECT 1 \gx`).
* Variable references `:name`, `:'name'`, `:"name"` and `:{?name}` are single tokens.
* The inline data of `COPY ... FROM STDIN;` and `\copy ... from stdin` is skipped, up to the line that contains only
  `\.`. The data rows are not compared by CPD.

## Suppression

Code can be excluded from the analysis with the comments `CPD-OFF` and `CPD-ON`, for example
`-- CPD-OFF` and `-- CPD-ON`.

## Ignoring literals and identifiers

The options `--ignore-literals` and `--ignore-identifiers` are supported. Literals are numbers and strings
(including the quoted arguments of psql commands). Both quoted and unquoted identifiers are ignored.
Keywords are never treated as identifiers.
