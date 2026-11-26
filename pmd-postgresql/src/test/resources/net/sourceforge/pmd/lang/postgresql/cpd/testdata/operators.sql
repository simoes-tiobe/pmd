SELECT a::int, a->b, a->>'k', a#>'{x}', a#>>'{x}', a@>b, a<@b, a||b, a!~*b, a<>b, a<=b, a>=b, a!=b;
SELECT 1 +--comment
SELECT 1 /*c*/ + 2;
SELECT a ~~ b, a !~~* b, a <-> b, a @@ b, a ?| b, a #- b, a := b, a => b;
