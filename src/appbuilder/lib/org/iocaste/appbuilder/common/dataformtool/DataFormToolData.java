package org.iocaste.appbuilder.common.dataformtool;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.appbuilder.common.AbstractComponentData;
import org.iocaste.appbuilder.common.ViewSpecItem.TYPES;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.ExtendedObject;

public class DataFormToolData extends AbstractComponentData {
    public ExtendedObject object;
    public String style, modelname;
    public DocumentModel model;
    public Map<String, DataFormToolItem> items;
    
    public DataFormToolData() {
        super(TYPES.DATA_FORM);
        items = new HashMap<>();
    }
    
    public final DataFormToolItem itemInstance(String name) {
        DataFormToolItem item = new DataFormToolItem();
        item.name = name;
        items.put(name, item);
        return item;
    }
}
