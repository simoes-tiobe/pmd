/*
 * BSD-style license; for more info see http://pmd.sourceforge.net/license.html
 */

package net.sourceforge.pmd.lang.postgresql.cpd;

import java.util.Locale;

import net.sourceforge.pmd.cpd.CpdLanguageProperties;
import net.sourceforge.pmd.cpd.impl.JavaccCpdLexer;
import net.sourceforge.pmd.lang.LanguagePropertyBundle;
import net.sourceforge.pmd.lang.TokenManager;
import net.sourceforge.pmd.lang.ast.impl.javacc.CharStream;
import net.sourceforge.pmd.lang.ast.impl.javacc.JavaccToken;
import net.sourceforge.pmd.lang.document.TextDocument;
import net.sourceforge.pmd.lang.postgresql.ast.PostgreSQLTokenKinds;

/**
 * The CPD lexer for PostgreSQL.
 *
 * <p>PostgreSQL folds unquoted identifiers to lower case, and keywords are case-insensitive. So all tokens are
 * lower-cased for CPD, except the ones where the case is significant: quoted identifiers, string literals,
 * dollar quotes and everything related to psql meta-commands.
 */
public class PostgreSQLCpdLexer extends JavaccCpdLexer {

    private final boolean ignoreIdentifiers;
    private final boolean ignoreLiterals;

    public PostgreSQLCpdLexer(LanguagePropertyBundle properties) {
        ignoreIdentifiers = properties.getProperty(CpdLanguageProperties.CPD_ANONYMIZE_IDENTIFIERS);
        ignoreLiterals = properties.getProperty(CpdLanguageProperties.CPD_ANONYMIZE_LITERALS);
    }

    @Override
    protected TokenManager<JavaccToken> makeLexerImpl(TextDocument doc) {
        return PostgreSQLTokenKinds.newTokenManager(CharStream.create(doc));
    }

    @Override
    protected String getImage(JavaccToken token) {
        int kind = token.kind;

        switch (kind) {
        case PostgreSQLTokenKinds.IDENTIFIER:
        case PostgreSQLTokenKinds.BODY_IDENTIFIER:
        case PostgreSQLTokenKinds.QUOTED_IDENTIFIER:
            if (ignoreIdentifiers) {
                // both kinds of unquoted identifier are the same for CPD
                return PostgreSQLTokenKinds.describe(PostgreSQLTokenKinds.IDENTIFIER);
            }
            break;
        case PostgreSQLTokenKinds.NUMBER:
        case PostgreSQLTokenKinds.STRING_LITERAL:
        case PostgreSQLTokenKinds.PSQL_STRING:
            if (ignoreLiterals) {
                return PostgreSQLTokenKinds.describe(kind);
            }
            break;
        default:
            break;
        }

        if (isCaseSensitive(kind)) {
            return token.getImage();
        }
        return token.getImage().toLowerCase(Locale.ROOT);
    }

    private static boolean isCaseSensitive(int kind) {
        switch (kind) {
        case PostgreSQLTokenKinds.QUOTED_IDENTIFIER:
        case PostgreSQLTokenKinds.STRING_LITERAL:
        case PostgreSQLTokenKinds.DOLLAR_QUOTE:
        case PostgreSQLTokenKinds.OTHER:
        case PostgreSQLTokenKinds.PSQL_COMMAND:
        case PostgreSQLTokenKinds.PSQL_SEPARATOR:
        case PostgreSQLTokenKinds.PSQL_VARIABLE:
        case PostgreSQLTokenKinds.PSQL_STRING:
        case PostgreSQLTokenKinds.PSQL_QUOTED:
        case PostgreSQLTokenKinds.PSQL_BACKTICK:
        case PostgreSQLTokenKinds.PSQL_WORD:
        case PostgreSQLTokenKinds.PSQL_OTHER:
            return true;
        default:
            return false;
        }
    }
}
