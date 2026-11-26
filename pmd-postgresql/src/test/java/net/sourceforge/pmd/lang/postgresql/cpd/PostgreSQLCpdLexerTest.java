/*
 * BSD-style license; for more info see http://pmd.sourceforge.net/license.html
 */

package net.sourceforge.pmd.lang.postgresql.cpd;

import org.junit.jupiter.api.Test;

import net.sourceforge.pmd.cpd.CpdLanguageProperties;
import net.sourceforge.pmd.lang.postgresql.PostgreSQLLanguageModule;
import net.sourceforge.pmd.lang.test.cpd.CpdTextComparisonTest;
import net.sourceforge.pmd.lang.test.cpd.LanguagePropertyConfig;

class PostgreSQLCpdLexerTest extends CpdTextComparisonTest {

    PostgreSQLCpdLexerTest() {
        super(PostgreSQLLanguageModule.getInstance(), ".sql");
    }

    @Test
    void testSample() {
        doTest("sample-postgresql");
    }

    @Test
    void testComments() {
        doTest("comments");
    }

    @Test
    void testSpecialComments() {
        doTest("specialComments");
    }

    @Test
    void testIdentifiers() {
        doTest("identifiers");
    }

    @Test
    void testStrings() {
        doTest("strings");
    }

    @Test
    void testDollarQuoting() {
        doTest("dollarQuoting");
    }

    @Test
    void testNumbers() {
        doTest("numbers");
    }

    @Test
    void testOperators() {
        doTest("operators");
    }

    @Test
    void testPsqlVariables() {
        doTest("psqlVariables");
    }

    @Test
    void testPsqlMetaCommands() {
        doTest("psqlMetaCommands");
    }

    @Test
    void testCopyFromStdin() {
        doTest("copyFromStdin");
    }

    @Test
    void testTabWidth() {
        doTest("tabWidth");
    }

    @Test
    void anonymizeLiterals() {
        doTest("sample-postgresql", "_ignore-literals", props -> props.setProperty(CpdLanguageProperties.CPD_ANONYMIZE_LITERALS, true));
    }

    @Test
    void anonymizeIdentifiers() {
        doTest("sample-postgresql", "_ignore-identifiers", ignoreIdentifiers());
    }

    @Test
    void anonymizeAll() {
        doTest("psqlMetaCommands", "_ignore-all", props -> {
            props.setProperty(CpdLanguageProperties.CPD_ANONYMIZE_LITERALS, true);
            props.setProperty(CpdLanguageProperties.CPD_ANONYMIZE_IDENTIFIERS, true);
        });
    }

    @Test
    void unterminatedBlockComment() {
        expectLexException("SELECT 1; /* outer /* inner */ not closed");
    }

    private LanguagePropertyConfig ignoreIdentifiers() {
        return props -> props.setProperty(CpdLanguageProperties.CPD_ANONYMIZE_IDENTIFIERS, true);
    }
}
