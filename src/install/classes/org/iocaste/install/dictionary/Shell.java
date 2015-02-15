package org.iocaste.install.dictionary;

import java.util.List;

public class Shell extends Module {
    
    public Shell(byte dbtype) {
        super(dbtype);
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.install.dictionary.Module#install()
     */
    @Override
    public List<String> install() {
        Table shell001, shell002, shell003, docs001, docs002, docs003, docs004;
        Table docs005;
        
        shell001 = tableInstance("SHELL001");
        shell001.key("SNAME", CHAR, 15);

        shell002 = tableInstance("SHELL002");
        shell002.key("EINDX", CHAR, 18);
        shell002.ref("SNAME", CHAR, 15, "SHELL001", "SNAME");
        shell002.add("ENAME", CHAR, 60);
        
        shell003 = tableInstance("SHELL003");
        shell003.key("PINDX", CHAR, 21);
        shell003.ref("EINDX", CHAR, 18, "SHELL002", "EINDX");
        shell003.ref("SNAME", CHAR, 15, "SHELL001", "SNAME");
        shell003.add("PNAME", CHAR, 60);
        shell003.add("VALUE", CHAR, 60);

        docs001 = getTable("DOCS001");
        docs002 = getTable("DOCS002");
        docs003 = getTable("DOCS003");
        docs004 = getTable("DOCS004");
        docs005 = getTable("DOCS005");

        insertElement(docs003,
                "STYLE.NAME", 0, 15, 0, true);
        insertElement(docs003,
                "STYLE_ELEMENT.INDEX", 0, 18, 0, true);
        insertElement(docs003,
                "STYLE_ELEMENT.NAME", 0, 15, 0, true);
        insertElement(docs003,
                "STYLE_ELEMENT_DETAIL.INDEX", 0, 21, 0, true);
        insertElement(docs003,
                "STYLE_ELEMENT_DETAIL.PROPERTY", 0, 60, 0, false);
        insertElement(docs003,
                "STYLE_ELEMENT_DETAIL.VALUE", 0, 60, 0, false);
        
        insertModel(docs001, docs005,
                "STYLE", "SHELL001", null);
        insertModelKey(docs002, docs004,
                "STYLE.NAME", "STYLE", "SNAME", "STYLE.NAME", null);

        insertModel(docs001, docs005,
                "STYLE_ELEMENT", "SHELL002", null);
        insertModelKey(docs002, docs004,
                "STYLE_ELEMENT.INDEX", "STYLE_ELEMENT", "EINDX",
                    "STYLE_ELEMENT.INDEX", null);
        insertModelItem(docs002,
                "STYLE_ELEMENT.NAME", "STYLE_ELEMENT", "ENAME",
                    "STYLE_ELEMENT.NAME", null);
        insertModelItem(docs002,
                "STYLE_ELEMENT.STYLE", "STYLE_ELEMENT", "SNAME", "STYLE.NAME",
                    null);
        
        insertModel(docs001, docs005,
                "STYLE_ELEMENT_DETAIL", "SHELL003", null);
        insertModelKey(docs002, docs004,
                "STYLE_ELEMENT_DETAIL.INDEX", "STYLE_ELEMENT_DETAIL", "PINDX",
                    "STYLE_ELEMENT_DETAIL.INDEX", null);
        insertModelItem(docs002,
                "STYLE_ELEMENT_DETAIL.ELEMENT", "STYLE_ELEMENT_DETAIL", "EINDX",
                    "STYLE_ELEMENT.INDEX", null);
        insertModelItem(docs002,
                "STYLE_ELEMENT_DETAIL.STYLE", "STYLE_ELEMENT_DETAIL", "SNAME",
                    "STYLE.NAME", null);
        insertModelItem(docs002,
                "STYLE_ELEMENT_DETAIL.PROPERTY", "STYLE_ELEMENT_DETAIL",
                    "PNAME", "STYLE_ELEMENT_DETAIL.PROPERTY", null);
        insertModelItem(docs002,
                "STYLE_ELEMENT_DETAIL.VALUE", "STYLE_ELEMENT_DETAIL", "VALUE",
                    "STYLE_ELEMENT_DETAIL.VALUE", null);
        return compile();
    }
    
}
