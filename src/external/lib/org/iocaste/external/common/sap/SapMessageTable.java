package org.iocaste.external.common.sap;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.documents.common.ExtendedObject;

public class SapMessageTable {
    private Map<String, List<Map<String, Object>>> tables;
    
    public SapMessageTable() {
        tables = new HashMap<>();
    }
    
    public final Map<String, List<Map<String, Object>>> getTables() {
        return tables;
    }
    
    public final void put(String name, Collection<ExtendedObject> items) {
        String mname;
        DocumentModel model;
        Map<String, Object> line;
        List<Map<String, Object>> lines;
        
        lines = new ArrayList<>();
        for (ExtendedObject item : items) {
            model = item.getModel();
            line = new HashMap<>();
            for (DocumentModelItem mitem : model.getItens()) {
                mname = mitem.getName();
                line.put(mname, item.get(mname));
            }
            lines.add(line);
        }
        
        tables.put(name, lines);
    }
}