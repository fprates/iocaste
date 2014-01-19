package org.iocaste.install.dictionary;

import java.util.List;

public class Package extends Module {

    public Package(byte dbtype) {
        super(dbtype);
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.install.dictionary.Module#install()
     */
    @Override
    public final List<String> install() {
        Table package001, package002;
        Table docs001, docs002, docs003, docs004, docs005, range001;
        
        package001 = tableInstance("PACKAGE001");
        package001.key("IDENT", CHAR, 60);
        package001.add("PKGID", NUMC, 12);

        package002 = tableInstance("PACKAGE002");
        package002.key("IDENT", NUMC, 12);
        package002.ref("PACKG", CHAR, 60, "PACKAGE001", "IDENT");
        package002.add("INAME", CHAR, 60);
        package002.add("MODEL", CHAR, 24);

        docs001 = getTable("DOCS001");
        docs002 = getTable("DOCS002");
        docs003 = getTable("DOCS003");
        docs004 = getTable("DOCS004");
        docs005 = getTable("DOCS005");
        range001 = getTable("RANGE001");
        
        insertModel(docs001, docs005, "PACKAGE", "PACKAGE001", null);
        insertElement(docs003, "PACKAGE.NAME", 0, 60, 0, false);
        insertElement(docs003, "PACKAGE.CODE", 0, 12, 3, false);
        insertModelKey(docs002, docs004, "PACKAGE.NAME", "PACKAGE", "IDENT",
                "PACKAGE.NAME", null);
        insertModelItem(docs002, "PACKAGE.CODE", "PACKAGE", "PKGID",
                "PACKAGE.CODE", null);

        insertModel(docs001, docs005, "PACKAGE_ITEM", "PACKAGE002", null);
        insertElement(docs003, "PACKAGE_ITEM.NAME", 0, 60, 0, true);
        insertElement(docs003, "PACKAGE_ITEM.MODEL", 0, 24, 0, true);
        insertModelKey(docs002, docs004, "PACKAGE_ITEM.CODE", "PACKAGE_ITEM",
                "IDENT", "PACKAGE.CODE", null);
        insertModelItem(docs002, "PACKAGE_ITEM.PACKAGE", "PACKAGE_ITEM",
                "PACKG", "PACKAGE.NAME", null);
        insertModelItem(docs002, "PACKAGE_ITEM.NAME", "PACKAGE_ITEM", "INAME",
                "PACKAGE_ITEM.NAME", null);
        insertModelItem(docs002, "PACKAGE_ITEM.MODEL", "PACKAGE_ITEM", "MODEL",
                "PACKAGE_ITEM.MODEL", null);

        range001.set("ident", "PKGCODE");
        range001.set("crrnt", 0);
        insert(range001);
        
        return compile();
    }
}
