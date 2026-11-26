/*
 * BSD-style license; for more info see http://pmd.sourceforge.net/license.html
 */

package net.sourceforge.pmd.lang.postgresql;

import net.sourceforge.pmd.cpd.CpdLanguageProperties;
import net.sourceforge.pmd.cpd.CpdLexer;
import net.sourceforge.pmd.lang.LanguagePropertyBundle;
import net.sourceforge.pmd.lang.LanguageRegistry;
import net.sourceforge.pmd.lang.impl.CpdOnlyLanguageModuleBase;
import net.sourceforge.pmd.lang.postgresql.cpd.PostgreSQLCpdLexer;

/**
 * Language module for PostgreSQL (SQL, PL/pgSQL and psql scripts). This language supports CPD only.
 */
public class PostgreSQLLanguageModule extends CpdOnlyLanguageModuleBase {
    private static final String ID = "postgresql";

    /**
     * Creates a new instance of {@link PostgreSQLLanguageModule} with the default
     * extensions for PostgreSQL files.
     */
    public PostgreSQLLanguageModule() {
        super(LanguageMetadata.withId(ID)
                              .name("PostgreSQL")
                              .extensions("sql", "pgsql", "psql"));
    }

    public static PostgreSQLLanguageModule getInstance() {
        return (PostgreSQLLanguageModule) LanguageRegistry.CPD.getLanguageById(ID);
    }

    @Override
    public LanguagePropertyBundle newPropertyBundle() {
        LanguagePropertyBundle bundle = super.newPropertyBundle();
        bundle.definePropertyDescriptor(CpdLanguageProperties.CPD_ANONYMIZE_LITERALS);
        bundle.definePropertyDescriptor(CpdLanguageProperties.CPD_ANONYMIZE_IDENTIFIERS);
        return bundle;
    }

    @Override
    public CpdLexer createCpdLexer(LanguagePropertyBundle bundle) {
        return new PostgreSQLCpdLexer(bundle);
    }
}
