package org.iocaste.install.dictionary;

import java.util.List;

import org.iocaste.documents.common.DataType;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.kernel.common.Table;

public class Documents extends Module {

    public Documents(byte dbtype) {
        super(dbtype);
    }

    /*
     * (non-Javadoc)
     * @see org.iocaste.install.dictionary.Module#install(
     *    org.iocaste.install.dictionary.ModuleContext)
     */
    @Override
    public final List<String> install(ModuleContext context) {
        Table range001, docs001, docs002, docs003, docs004, docs005, docs006;
        Table range002;
        String rangeid;
        
        range001 = tableInstance("RANGE001");
        range001.key("NMSPC", DataType.CHAR, 128);
        range001.key("IDENT", DataType.CHAR, 12);
        range001.add("CRRNT", DataType.NUMC, 12);
        
        range002 = tableInstance("RANGE002");
        range002.key("NMSPC", DataType.CHAR, 128);
        range002.key("SERIE", DataType.CHAR, 14);
        range002.add("RNGID", DataType.CHAR, 12);
        range002.add("CRRNT", DataType.NUMC, 12);
        range002.constraint("RANGE002_RNGID_RANGE001", "RANGE001",
                new String[] {"NMSPC", "RNGID"},
                new String[] {"NMSPC", "IDENT"});
        
        docs001 = tableInstance("DOCS001");
        docs001.key("DOCID", DataType.CHAR, 24);
        docs001.add("TNAME", DataType.CHAR, 24);
        docs001.add("CLASS", DataType.CHAR, 255);
        docs001.add("NSCOL", DataType.CHAR, 48);
        docs001.add("NSTYP", DataType.NUMC, 1);
        docs001.add("NSLEN", DataType.NUMC, 4);
        docs001.add("PKGNM", DataType.CHAR, 60);

        docs003 = tableInstance("DOCS003");
        docs003.key("ENAME", DataType.CHAR, 48);
        docs003.add("DECIM", DataType.NUMC, 2);
        docs003.add("LNGTH", DataType.NUMC, 4);
        docs003.add("ETYPE", DataType.NUMC, 1);
        docs003.add("UPCAS", DataType.BOOLEAN, 1);
        docs003.add("ATYPE", DataType.NUMC, 2);

        docs002 = tableInstance("DOCS002");
        docs002.key("ITMID", DataType.CHAR, 48);
        docs002.ref("DOCID", DataType.CHAR, 24, "DOCS001", "DOCID");
        docs002.add("ITMNM", DataType.CHAR, 24);
        docs002.add("NRITM", DataType.NUMC, 3);
        docs002.add("FNAME", DataType.CHAR, DocumentModelItem.MAX_FNAME_LEN);
        docs002.ref("ENAME", DataType.CHAR, 48, "DOCS003", "ENAME");
        docs002.add("ATTRB", DataType.CHAR, 64);
        docs002.add("ITREF", DataType.CHAR, 48);

        docs004 = tableInstance("DOCS004");
        docs004.key("ITMID", DataType.CHAR, 48);
        docs004.ref("DOCID", DataType.CHAR, 24, "DOCS001", "DOCID");

        docs005 = tableInstance("DOCS005");
        docs005.key("TNAME", DataType.CHAR, 24);
        docs005.ref("DOCID", DataType.CHAR, 24, "DOCS001", "DOCID");

        docs006 = tableInstance("DOCS006");
        docs006.key("ITMID", DataType.CHAR, 48);
        docs006.ref("ITREF", DataType.CHAR, 48, "DOCS002", "ITMID");

        insertElement(docs003,
                "MODEL.NAME", 0, 24, 0, true);
        insertElement(docs003,
                "MODEL.TABLE", 0, 24, 0, true);
        insertElement(docs003,
                "MODEL.CLASS", 0, 255, 0, false);
        insertElement(docs003,
                "DATAELEMENT.LENGTH", 0, 4, 3, false);
        insertElement(docs003,
                "DATAELEMENT.TYPE", 0, 1, 3, false);
        insertElement(docs003,
                "DATAELEMENT.UPCASE", 0, 1, 5, false);
        insertElement(docs003,
                "MODEL.PACKAGE", 0, 60, 0, false);
        insertElement(docs003,
                "DATAELEMENT.NAME", 0, 48, 0, true);
        insertElement(docs003,
                "DATAELEMENT.DECIMALS", 0, 2, 3, false);
        insertElement(docs003,
                "DATAELEMENT.ATTRIBTYPE", 0, 1, 3, false);
        insertElement(docs003,
                "MODELITEM.ID", 0, 48, 0, true);
        insertElement(docs003,
                "MODELITEM.NAME", 0, 24, 0, true);
        insertElement(docs003,
                "MODELITEM.FIELDNAME", 0, DocumentModelItem.MAX_FNAME_LEN, 0,
                        true);
        insertElement(docs003,
                "MODELITEM.INDEX", 1, 3, 3, false);
        insertElement(docs003,
                "MODELITEM.ATTRIB", 0, 64, 0, false);
        insertElement(docs003,
                "NUMBER_RANGE.IDENT", 0, 12, 0, true);
        insertElement(docs003,
                "NUMBER_RANGE.CURRENT", 0, 12, 3, false);
        insertElement(docs003,
                "NUMBER_SERIES.SERIE", 0, 14, 0, true);
        
        /*
         * Models
         */
        insertModel(docs001, docs005, "MODEL", "DOCS001",
                "org.iocaste.documents.common.DocumentModel");
        context.modelname = insertModelKey(docs001, docs002, docs004,
                "NAME", "DOCID", "MODEL.NAME", "name");
        insertModelItem(docs001, docs002,
                "TABLE", "TNAME", "MODEL.TABLE", "tableName");
        insertModelItem(docs001, docs002,
                "CLASS", "CLASS", "MODEL.CLASS", "className");
        insertModelItem(docs001, docs002,
                "NAMESPACE", "NSCOL", "MODELITEM.FIELDNAME", null);
        insertModelItem(docs001, docs002,
                "NS_TYPE", "NSTYP", "DATAELEMENT.TYPE", null);
        insertModelItem(docs001, docs002,
                "NS_LENGTH", "NSLEN", "DATAELEMENT.LENGTH", null);
        insertModelItem(docs001, docs002,
                "PACKAGE", "PKGNM", "MODEL.PACKAGE", null);
        
        /*
         * Data elements
         */
        insertModel(docs001, docs005, "DATAELEMENT", "DOCS003",
                "org.iocaste.documents.common.DataElement");
        insertModelKey(docs001, docs002, docs004,
                "NAME", "ENAME", "DATAELEMENT.NAME", "name");
        insertModelItem(docs001, docs002,
                "DECIMALS", "DECIM", "DATAELEMENT.DECIMALS", "decimals");
        insertModelItem(docs001, docs002,
                "LENGTH", "LNGTH", "DATAELEMENT.LENGTH", "length");
        insertModelItem(docs001, docs002,
                "TYPE", "ETYPE", "DATAELEMENT.TYPE", "type");
        insertModelItem(docs001, docs002,
                "UPCASE", "UPCAS", "DATAELEMENT.UPCASE", "upcase");
        insertModelItem(docs001, docs002,
                "ATTRIBTYPE", "ATYPE","DATAELEMENT.ATTRIBTYPE","attributeType");

        /*
         * Models items
         */
        insertModel(docs001, docs005,
                "MODELITEM", "DOCS002",
                "org.iocaste.documents.common.DocumentModelItem");
        context.itemname = insertModelKey(docs001, docs002, docs004,
                "ID", "ITMID", "MODELITEM.ID", null);
        insertModelItem(docs001, docs002, docs006,
            "MODEL", "DOCID", "MODEL.NAME", "documentModel", context.modelname);
        insertModelItem(docs001, docs002,
                "INDEX", "NRITM", "MODELITEM.INDEX", "index");
        insertModelItem(docs001, docs002,
                "NAME", "ITMNM", "MODELITEM.NAME", "name");
        insertModelItem(docs001, docs002,
                "FIELDNAME", "FNAME", "MODELITEM.FIELDNAME", "tableFieldName");
        insertModelItem(docs001, docs002,
                "ELEMENT", "ENAME", "DATAELEMENT.NAME", "dataElement");
        insertModelItem(docs001, docs002,
                "ATTRIB", "ATTRB", "MODELITEM.ATTRIB", "attributeName");
        insertModelItem(docs001, docs002,
                "ITEM_REF", "ITREF", "MODELITEM.ID", "reference");

        /*
         * Models keys
         */
        insertModel(docs001, docs005,
                "MODEL_KEYS", "DOCS004", null);
        insertModelKey(docs001, docs002, docs004,
                "NAME", "ITMID", "MODELITEM.ID", null);
        insertModelItem(docs001, docs002,
                "MODEL", "DOCID", "MODEL.NAME", null);

        /*
         * Reverse model-table
         */
        insertModel(docs001, docs005,
                "TABLE_MODEL", "DOCS005", null);
        insertModelKey(docs001, docs002, docs004,
                "TABLE", "TNAME", "MODEL.TABLE", "tableName");
        insertModelItem(docs001, docs002, docs006,
                "MODEL", "DOCID", "MODEL.NAME", "model", context.modelname);

        /*
         * Foreign keys
         */
        insertModel(docs001, docs005,
                "FOREIGN_KEY", "DOCS006", null);
        insertModelKey(docs001, docs002, docs004,
                "ITEM_NAME", "ITREF", "MODELITEM.ID", "itemName");
        insertModelItem(docs001, docs002, docs006,
                "REFERENCE", "ITMID", "MODELITEM.ID", "reference",
                context.itemname);

        /*
         * range
         */
        docs001.set("nscol", "NMSPC");
        docs001.set("nstyp", 0);
        docs001.set("nslen", 128);
        insertModel(docs001, docs005,
                "NUMBER_RANGE", "RANGE001", null);
        rangeid = insertModelKey(docs001, docs002, docs004,
                "IDENT", "IDENT", "NUMBER_RANGE.IDENT", null);
        insertModelItem(docs001, docs002,
                "CURRENT", "CRRNT", "NUMBER_RANGE.CURRENT", null);
        docs001.clear();
        
        /*
         * range number series
         */
        docs001.set("nscol", "NMSPC");
        docs001.set("nstyp", 0);
        docs001.set("nslen", 128);
        insertModel(docs001, docs005,
                "NUMBER_SERIES", "RANGE002", null);
        insertModelKey(docs001, docs002, docs004,
                "SERIE", "SERIE", "NUMBER_SERIES.SERIE", null);
        insertModelItem(docs001, docs002, docs006,
                "RANGE", "RNGID", "NUMBER_RANGE.IDENT", null, rangeid);
        insertModelItem(docs001, docs002,
                "CURRENT", "CRRNT", "NUMBER_RANGE.CURRENT", null);
        docs001.clear();
        
        range001.set("nmspc", "");
        range001.set("ident", "AUTHINDEX");
        range001.set("crrnt", 5);
        insert(range001);

        range001.set("nmspc", "");
        range001.set("ident", "PROFILEINDEX");
        range001.set("crrnt", 2);
        insert(range001);
        
        return compile();
    }
}
