package org.iocaste.install.dictionary;

import java.util.List;

import org.iocaste.documents.common.DataType;
import org.iocaste.kernel.common.Table;

public class SH extends Module {

    public SH(byte dbtype) {
        super(dbtype);
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.install.dictionary.Module#install()
     */
    @Override
    public final List<String> install() {
        Table shcab, shitm, shref, shqry;
        Table docs001, docs002, docs003, docs004, docs005, docs006;
        
        shcab = tableInstance("SHCAB");
        shcab.key("IDENT", DataType.CHAR, 24);
        shcab.ref("DOCID", DataType.CHAR, 24, "DOCS001", "DOCID");
        shcab.ref("EXPRT", DataType.CHAR, 48, "DOCS002", "INAME");

        shitm = tableInstance("SHITM");
        shitm.key("INAME", DataType.CHAR, 48);
        shitm.ref("SHCAB", DataType.CHAR, 24, "SHCAB", "IDENT");
        shitm.ref("MDITM", DataType.CHAR, 48, "DOCS002", "INAME");
        
        shref = tableInstance("SHREF");
        shref.key("INAME", DataType.CHAR, 48);
        shref.add("SHCAB", DataType.CHAR, 24);

        shqry = tableInstance("SHQRY");
        shqry.key("QRYID", DataType.CHAR, 27);
        shqry.ref("SHCAB", DataType.CHAR, 24, "SHCAB", "IDENT");
        shqry.add("FIELD", DataType.CHAR, 24);
        shqry.add("CNDTN", DataType.NUMC, 2);
        shqry.add("VALUE", DataType.CHAR, 255);
        shqry.add("OPRTR", DataType.CHAR, 3);
        
        shcab.set("ident", "SH_MODEL");
        shcab.set("docid", "MODEL");
        shcab.set("exprt", "MODEL.NAME");
        insert(shcab);
        
        shitm.set("iname", "SH_MODEL.NAME");
        shitm.set("shcab", "SH_MODEL");
        shitm.set("mditm", "MODEL.NAME");
        insert(shitm);
        
        shref.set("iname", "MODEL.NAME");
        shref.set("shcab", "SH_MODEL");
        insert(shref);
        
        docs001 = getTable("DOCS001");
        docs002 = getTable("DOCS002");
        docs003 = getTable("DOCS003");
        docs004 = getTable("DOCS004");
        docs005 = getTable("DOCS005");
        docs006 = getTable("DOCS006");
        
        insertModel(docs001, docs005, "SEARCH_HELP", "SHCAB", null);
        insertElement(docs003, "SEARCH_HELP.NAME", 0, 12, 0, true);
        insertModelKey(docs002, docs004, "SEARCH_HELP.NAME", "SEARCH_HELP",
                "IDENT", "SEARCH_HELP.NAME", "name");
        insertModelItem(docs002, docs006, "SEARCH_HELP.MODEL", "SEARCH_HELP",
                "DOCID", "MODEL.NAME", "model", "MODEL.NAME");
        insertModelItem(docs002, docs006, "SEARCH_HELP.EXPORT", "SEARCH_HELP",
                "EXPRT", "MODELITEM.NAME", "export", "MODELITEM.NAME");

        insertModel(docs001, docs005, "SH_ITENS", "SHITM", null);
        insertElement(docs003, "SH_ITENS.NAME", 0, 48, 0, true);
        insertModelKey(docs002, docs004, "SH_ITENS.NAME", "SH_ITENS",
                "INAME", "SH_ITENS.NAME", "name");
        insertModelItem(docs002, docs006, "SH_ITENS.SEARCH_HELP", "SH_ITENS",
                "SHCAB", "SEARCH_HELP.NAME", "searchHelp", "SEARCH_HELP.NAME");
        insertModelItem(docs002, docs006, "SH_ITENS.ITEM", "SH_ITENS",
                "MDITM", "MODELITEM.NAME", "modelItem", "MODELITEM.NAME");
        
        insertModel(docs001, docs005, "SH_REFERENCE", "SHREF", null);
        insertModelKey(docs002, docs004, "SH_REFERENCE.MODEL_ITEM",
                "SH_REFERENCE", "INAME", "MODELITEM.NAME", "modelItem");
        insertModelItem(docs002, docs006, "SH_REFERENCE.SEARCH_HELP",
               "SH_REFERENCE", "SHCAB", "SEARCH_HELP.NAME", "searchHelp", null);

        insertModel(docs001, docs005, "SH_QUERIES", "SHQRY", null);
        insertElement(docs003, "SH_QUERY_ID", 0, 27, DataType.CHAR, true);
        insertElement(docs003, "SH_QUERY_FIELD", 0, 24, DataType.CHAR, true);
        insertElement(docs003, "SH_QUERY_CONDITION", 0, 3, DataType.CHAR,false);
        insertElement(docs003, "SH_QUERY_VALUE", 0, 255, DataType.CHAR, true);
        insertElement(docs003, "SH_QUERY_OPERATOR", 0, 2, DataType.NUMC, false);
        insertModelKey(docs002, docs004, "SH_QUERIES.ID", "SH_QUERIES",
                "QRYID", "SH_QUERY_ID", null);
        insertModelItem(docs002,docs006, "SH_QUERIES.SEARCH_HELP", "SH_QUERIES",
                "SHCAB", "SEARCH_HELP.NAME", null, "SEARCH_HELP.NAME");
        insertModelItem(docs002, docs006, "SH_QUERIES.FIELD", "SH_QUERIES",
                "FIELD", "SH_QUERY_FIELD", null, null);
        insertModelItem(docs002, docs006, "SH_QUERIES.CONDITION", "SH_QUERIES",
                "CNDTN", "SH_QUERY_CONDITION", null, null);
        insertModelItem(docs002, docs006, "SH_QUERIES.VALUE", "SH_QUERIES",
                "VALUE", "SH_QUERY_VALUE", null, null);
        insertModelItem(docs002, docs006, "SH_QUERIES.OPERATOR", "SH_QUERIES",
                "OPRTR", "SH_QUERY_OPERATOR", null, null);
        
        return compile();
    }
}
