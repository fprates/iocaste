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
     * @see org.iocaste.install.dictionary.Module#install(
     *    org.iocaste.install.dictionary.ModuleContext)
     */
    @Override
    public final List<String> install(ModuleContext context) {
        Table shcab, shitm, shref, shqry;
        Table docs001, docs002, docs003, docs004, docs005, docs006;
        String shname;
        
        shcab = tableInstance("SHCAB");
        shcab.key("IDENT", DataType.CHAR, 24);
        shcab.ref("DOCID", DataType.CHAR, 24, "DOCS001", "DOCID");
        shcab.ref("EXPRT", DataType.CHAR, 48, "DOCS002", "ITMID");

        shitm = tableInstance("SHITM");
        shitm.key("INAME", DataType.CHAR, 48);
        shitm.ref("SHCAB", DataType.CHAR, 24, "SHCAB", "IDENT");
        shitm.add("SHITM", DataType.CHAR, 24);
        shitm.ref("MDITM", DataType.CHAR, 48, "DOCS002", "ITMID");
        
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
        shcab.set("exprt", context.modelname);
        insert(shcab);
        
        shitm.set("iname", "SH_MODEL001");
        shitm.set("shcab", "SH_MODEL");
        shitm.set("shitm", "NAME");
        shitm.set("mditm", context.modelname);
        insert(shitm);
        
        shref.set("iname", context.modelname);
        shref.set("shcab", "SH_MODEL");
        insert(shref);
        
        docs001 = getTable("DOCS001");
        docs002 = getTable("DOCS002");
        docs003 = getTable("DOCS003");
        docs004 = getTable("DOCS004");
        docs005 = getTable("DOCS005");
        docs006 = getTable("DOCS006");

        insertElement(docs003, "SEARCH_HELP.NAME", 0, 12, 0, true);
        insertElement(docs003, "SH_ITENS.NAME", 0, 48, 0, true);
        insertElement(docs003, "SH_QUERY_ID", 0, 27, DataType.CHAR, true);
        insertElement(docs003, "SH_QUERY_FIELD", 0, 24, DataType.CHAR, true);
        insertElement(docs003, "SH_QUERY_CONDITION", 0, 3, DataType.CHAR,false);
        insertElement(docs003, "SH_QUERY_VALUE", 0, 255, DataType.CHAR, true);
        insertElement(docs003, "SH_QUERY_OPERATOR", 0, 2, DataType.NUMC, false);
        
        insertModel(docs001, docs005,
                "SEARCH_HELP", "SHCAB", null);
        shname = insertModelKey(docs001, docs002, docs004,
                "NAME", "IDENT", "SEARCH_HELP.NAME", "name");
        insertModelItem(docs001, docs002, docs006,
                "MODEL", "DOCID", "MODEL.NAME", "model", context.modelname);
        insertModelItem(docs001, docs002, docs006,
               "EXPORT", "EXPRT", "MODELITEM.NAME", "export", context.itemname);

        insertModel(docs001, docs005,
                "SH_ITENS", "SHITM", null);
        insertModelKey(docs001, docs002, docs004,
                "NAME", "INAME", "SH_ITENS.NAME", "name");
        insertModelItem(docs001, docs002, docs006,
              "SEARCH_HELP", "SHCAB", "SEARCH_HELP.NAME", "searchHelp", shname);
        insertModelItem(docs001, docs002, docs006,
              "ITEM", "MDITM", "MODELITEM.NAME", "modelItem", context.itemname);
        
        insertModel(docs001, docs005,
                "SH_REFERENCE", "SHREF", null);
        insertModelKey(docs001, docs002, docs004,
                "MODEL_ITEM", "INAME", "MODELITEM.NAME", "modelItem");
        insertModelItem(docs001, docs002, docs006,
                "SEARCH_HELP", "SHCAB", "SEARCH_HELP.NAME", "searchHelp", null);

        insertModel(docs001, docs005,
                "SH_QUERIES", "SHQRY", null);
        insertModelKey(docs001, docs002, docs004,
                "ID", "QRYID", "SH_QUERY_ID", null);
        insertModelItem(docs001, docs002,docs006,
                "SEARCH_HELP", "SHCAB", "SEARCH_HELP.NAME", null, shname);
        insertModelItem(docs001, docs002, docs006,
                "FIELD", "FIELD", "SH_QUERY_FIELD", null, null);
        insertModelItem(docs001, docs002, docs006,
                "CONDITION", "CNDTN", "SH_QUERY_CONDITION", null, null);
        insertModelItem(docs001, docs002, docs006,
                "VALUE", "VALUE", "SH_QUERY_VALUE", null, null);
        insertModelItem(docs001, docs002, docs006,
                "OPERATOR", "OPRTR", "SH_QUERY_OPERATOR", null, null);
        
        return compile();
    }
}
