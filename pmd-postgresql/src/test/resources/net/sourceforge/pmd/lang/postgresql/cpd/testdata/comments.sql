SELECT 1; -- line comment
/* block */ SELECT 2;
/* outer /* inner */ still comment */ SELECT 3;
/**/ SELECT 4;
SELECT 5 /* multi
line */ + 6;
SELECT '/* not a comment */', '-- neither';
