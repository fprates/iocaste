package org.iocaste.appbuilder.common.cmodelviewer;

import java.util.LinkedHashMap;
import java.util.Map;

import org.iocaste.documents.common.ExtendedObject;

public class TableToolContextEntry {
    public static final byte DOCUMENT = 0;
    public static final byte BUFFER = 1;
    public Map<Integer, ExtendedObject> items;
    public String cmodelitem;
    public byte source;
    
    public TableToolContextEntry() {
        source = TableToolContextEntry.DOCUMENT;
        items = new LinkedHashMap<>();
    }
}