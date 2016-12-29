package org.iocaste.install.dictionary;

import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import org.iocaste.documents.common.DataType;
import org.iocaste.kernel.common.Table;

public class Package extends Module {

    public Package(byte dbtype) {
        super(dbtype);
    }

    /*
     * (non-Javadoc)
     * @see org.iocaste.install.dictionary.Module#install(
     *    org.iocaste.install.dictionary.ModuleContext)
     */
    @Override
    public final List<String> install(ModuleContext context) {
        Set<String> locales;
        String country, tag, packagename;
        Table language, package001, package002;
        Table docs001, docs002, docs003, docs004, docs005, docs006;
        
        language = tableInstance("LANG");
        language.key("LOCAL", DataType.CHAR, 5);
        
        package001 = tableInstance("PACKAGE001");
        package001.key("IDENT", DataType.CHAR, 60);

        package002 = tableInstance("PACKAGE002");
        package002.key("IDENT", DataType.CHAR, 72);
        package002.ref("PACKG", DataType.CHAR, 60, "PACKAGE001", "IDENT");
        package002.add("INAME", DataType.CHAR, 60);
        package002.add("MODEL", DataType.CHAR, 24);

        docs001 = getTable("DOCS001");
        docs002 = getTable("DOCS002");
        docs003 = getTable("DOCS003");
        docs004 = getTable("DOCS004");
        docs005 = getTable("DOCS005");
        docs006 = getTable("DOCS006");

        insertElement(docs003,
                "LANGUAGES.LOCALE", 0, 5, 0, false);
        insertElement(docs003,
                "PACKAGE.NAME", 0, 60, 0, false);
        insertElement(docs003,
                "PACKAGE_ITEM.CODE", 0, 72, 0, true);
        insertElement(docs003,
                "PACKAGE_ITEM.NAME", 0, 60, 0, true);
        insertElement(docs003,
                "PACKAGE_ITEM.MODEL", 0, 24, 0, true);
        
        /*
         * languages
         */
        insertModel(docs001, docs005,
                "LANGUAGES", "LANG", null);
        insertModelKey(docs001, docs002, docs004,
                "LOCALE", "LOCAL", "LANGUAGES.LOCALE", null);
        
        locales = new HashSet<>();
        for (Locale locale : Locale.getAvailableLocales()) {
            country = locale.getCountry();
            if (country.length() == 0)
                continue;
            
            tag = new StringBuilder(locale.getLanguage()).
                    append("_").
                    append(country).toString();
            if (locales.contains(tag))
                continue;
            
            locales.add(tag);
            language.set("local", tag);
            insert(language);
        }
        
        /*
         * package head
         */
        insertModel(docs001, docs005,
                "PACKAGE", "PACKAGE001", null);
        packagename = insertModelKey(docs001, docs002, docs004,
                "NAME", "IDENT", "PACKAGE.NAME", null);

        /*
         * package item
         */
        insertModel(docs001, docs005,
                "PACKAGE_ITEM", "PACKAGE002", null);
        insertModelKey(docs001, docs002, docs004,
                "CODE", "IDENT", "PACKAGE_ITEM.CODE", null);
        insertModelItem(docs001, docs002, docs006,
                "PACKAGE", "PACKG", "PACKAGE.NAME", null, packagename);
        insertModelItem(docs001, docs002,
                "NAME", "INAME", "PACKAGE_ITEM.NAME", null);
        insertModelItem(docs001, docs002,
                "MODEL", "MODEL", "PACKAGE_ITEM.MODEL", null);
        
        return compile();
    }
}
