package org.iocaste.install.dictionary;

import java.util.List;

import org.iocaste.documents.common.DataType;
import org.iocaste.kernel.common.Table;

public class Documents extends Module {

    public Documents(byte dbtype) {
        super(dbtype);
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.install.dictionary.Module#install()
     */
    @Override
    public final List<String> install() {
        Table range001, docs001, docs002, docs003, docs004, docs005, docs006;
        Table range002;
        
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
        docs002.key("INAME", DataType.CHAR, 48);
        docs002.ref("DOCID", DataType.CHAR, 24, "DOCS001", "DOCID");
        docs002.add("NRITM", DataType.NUMC, 3);
        docs002.add("FNAME", DataType.CHAR, 24);
        docs002.ref("ENAME", DataType.CHAR, 48, "DOCS003", "ENAME");
        docs002.add("ATTRB", DataType.CHAR, 64);
        docs002.add("ITREF", DataType.CHAR, 48);

        docs004 = tableInstance("DOCS004");
        docs004.key("INAME", DataType.CHAR, 48);
        docs004.ref("DOCID", DataType.CHAR, 24, "DOCS001", "DOCID");

        docs005 = tableInstance("DOCS005");
        docs005.key("TNAME", DataType.CHAR, 24);
        docs005.ref("DOCID", DataType.CHAR, 24, "DOCS001", "DOCID");

        docs006 = tableInstance("DOCS006");
        docs006.key("INAME", DataType.CHAR, 48);
        docs006.ref("ITREF", DataType.CHAR, 48, "DOCS002", "INAME");

        /*
         * Models
         */
        insertModel(docs001, docs005,
                "MODEL", "DOCS001",
                "org.iocaste.documents.common.DocumentModel");
        insertElement(docs003,
                "MODEL.NAME", 0, 24, 0, true);
        insertElement(docs003,
                "MODEL.TABLE", 0, 24, 0, true);
        insertElement(docs003,
                "MODEL.CLASS", 0, 255, 0, false);
        insertElement(docs003,
                "MODELITEM.FIELDNAME", 0, 24, 0, true);
        insertElement(docs003,
                "DATAELEMENT.LENGTH", 0, 4, 3, false);
        insertElement(docs003,
                "DATAELEMENT.TYPE", 0, 1, 3, false);
        insertElement(docs003,
                "DATAELEMENT.UPCASE", 0, 1, 5, false);
        insertElement(docs003,
                "MODEL.PACKAGE", 0, 60, 0, false);
        insertModelKey(docs002, docs004,
                "MODEL.NAME", "MODEL", "DOCID", "MODEL.NAME", "name");
        insertModelItem(docs002,
                "MODEL.TABLE", "MODEL", "TNAME", "MODEL.TABLE", "tableName");
        insertModelItem(docs002,
                "MODEL.CLASS", "MODEL", "CLASS", "MODEL.CLASS", "className");
        insertModelItem(docs002,
                "MODEL.NAMESPACE", "MODEL", "NSCOL", "MODELITEM.FIELDNAME",
                        null);
        insertModelItem(docs002,
                "MODEL.NS_TYPE", "MODEL", "NSTYP", "DATAELEMENT.TYPE", null);
        insertModelItem(docs002,
                "MODEL.NS_LENGTH", "MODEL", "NSLEN", "DATAELEMENT.LENGTH",
                        null);
        insertModelItem(docs002,
                "MODEL.PACKAGE", "MODEL", "PKGNM", "MODEL.PACKAGE", null);
        
        /*
         * Data elements
         */
        insertModel(docs001, docs005,
                "DATAELEMENT", "DOCS003",
                        "org.iocaste.documents.common.DataElement");
        insertElement(docs003,
                "DATAELEMENT.NAME", 0, 48, 0, true);
        insertElement(docs003,
                "DATAELEMENT.DECIMALS", 0, 2, 3, false);
        insertElement(docs003,
                "DATAELEMENT.ATTRIBTYPE", 0, 1, 3, false);
        insertModelKey(docs002, docs004,
                "DATAELEMENT.NAME", "DATAELEMENT", "ENAME", "DATAELEMENT.NAME",
                        "name");
        insertModelItem(docs002,
                "DATAELEMENT.DECIMALS", "DATAELEMENT", "DECIM",
                        "DATAELEMENT.DECIMALS", "decimals");
        insertModelItem(docs002,
                "DATAELEMENT.LENGTH", "DATAELEMENT", "LNGTH",
                        "DATAELEMENT.LENGTH", "length");
        insertModelItem(docs002,
                "DATAELEMENT.TYPE", "DATAELEMENT", "ETYPE", "DATAELEMENT.TYPE",
                        "type");
        insertModelItem(docs002,
                "DATAELEMENT.UPCASE", "DATAELEMENT", "UPCAS",
                        "DATAELEMENT.UPCASE", "upcase");
        insertModelItem(docs002,
                "DATAELEMENT.ATTRIBTYPE", "DATAELEMENT", "ATYPE",
                        "DATAELEMENT.ATTRIBTYPE", "attributeType");

        /*
         * Models items
         */
        insertModel(docs001, docs005,
                "MODELITEM", "DOCS002",
                "org.iocaste.documents.common.DocumentModelItem");
        insertElement(docs003,
                "MODELITEM.NAME", 0, 48, 0, true);
        insertElement(docs003,
                "MODELITEM.INDEX", 1, 3, 3, false);
        insertElement(docs003,
                "MODELITEM.ATTRIB", 0, 64, 0, false);
        insertModelKey(docs002, docs004, "MODELITEM.NAME", "MODELITEM",
                "INAME", "MODELITEM.NAME", "name");
        insertModelItem(docs002, docs006, "MODELITEM.MODEL", "MODELITEM",
                "DOCID", "MODEL.NAME", "documentModel", "MODEL.NAME");
        insertModelItem(docs002, "MODELITEM.INDEX", "MODELITEM", "NRITM",
                "MODELITEM.INDEX", "index");
        insertModelItem(docs002, "MODELITEM.FIELDNAME", "MODELITEM",
                "FNAME", "MODELITEM.FIELDNAME", "tableFieldName");
        insertModelItem(docs002, "MODELITEM.ELEMENT", "MODELITEM",
                "ENAME", "DATAELEMENT.NAME", "dataElement");
        insertModelItem(docs002, "MODELITEM.ATTRIB", "MODELITEM",
                "ATTRB", "MODELITEM.ATTRIB", "attributeName");
        insertModelItem(docs002, "MODELITEM.ITEM_REF", "MODELITEM",
                "ITREF", "MODELITEM.NAME", "reference");

        /*
         * Models keys
         */
        insertModel(docs001, docs005,
                "MODEL_KEYS", "DOCS004", null);
        insertModelKey(docs002, docs004, "MODEL_KEYS.NAME", "MODEL_KEYS",
                "INAME", "MODELITEM.NAME", null);
        insertModelItem(docs002, "MODEL_KEYS.MODEL", "MODEL_KEYS",
                "DOCID", "MODEL.NAME", null);

        /*
         * Reverse model-table
         */
        insertModel(docs001, docs005,
                "TABLE_MODEL", "DOCS005", null);
        insertModelKey(docs002, docs004, "TABLE_MODEL.TABLE", "TABLE_MODEL",
                "TNAME", "MODEL.TABLE", "tableName");
        insertModelItem(docs002, docs006, "TABLE_MODEL.MODEL", "TABLE_MODEL",
                "DOCID", "MODEL.NAME", "model", "MODEL.NAME");

        /*
         * Foreign keys
         */
        insertModel(docs001, docs005,
                "FOREIGN_KEY", "DOCS006", null);
        insertModelKey(docs002, docs004,
                "FOREIGN_KEY.ITEM_NAME", "FOREIGN_KEY", "ITREF",
                "MODELITEM.NAME", "itemName");
        insertModelItem(docs002, docs006,
                "FOREIGN_KEY.REFERENCE", "FOREIGN_KEY", "INAME",
                "MODELITEM.NAME", "reference", "MODELITEM.NAME");

        /*
         * range
         */
        insertElement(docs003, "NUMBER_RANGE.IDENT", 0, 12, 0, true);
        insertElement(docs003, "NUMBER_RANGE.CURRENT", 0, 12, 3, false);
        insertElement(docs003, "NUMBER_SERIES.SERIE", 0, 14, 0, true);

        docs001.set("nscol", "NMSPC");
        docs001.set("nstyp", 0);
        docs001.set("nslen", 128);
        insertModel(docs001, docs005,
                "NUMBER_RANGE", "RANGE001", null);
        insertModelKey(docs002, docs004,
                "NUMBER_RANGE.IDENT", "NUMBER_RANGE", "IDENT",
                        "NUMBER_RANGE.IDENT", null);
        insertModelItem(docs002,
                "NUMBER_RANGE.CURRENT", "NUMBER_RANGE", "CRRNT",
                        "NUMBER_RANGE.CURRENT", null);
        docs001.clear();
        
        /*
         * range number series
         */
        docs001.set("nscol", "NMSPC");
        docs001.set("nstyp", 0);
        docs001.set("nslen", 128);
        insertModel(docs001, docs005,
                "NUMBER_SERIES", "RANGE002", null);
        insertModelKey(docs002, docs004,
                "NUMBER_SERIES.SERIE", "NUMBER_SERIES", "SERIE",
                "NUMBER_SERIES.SERIE", null);
        insertModelItem(docs002, docs006,
                "NUMBER_SERIES.RANGE", "NUMBER_SERIES", "RNGID",
                "NUMBER_RANGE.IDENT", null, "NUMBER_RANGE.IDENT");
        insertModelItem(docs002,
                "NUMBER_SERIES.CURRENT", "NUMBER_SERIES", "CRRNT",
                "NUMBER_RANGE.CURRENT", null);
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
