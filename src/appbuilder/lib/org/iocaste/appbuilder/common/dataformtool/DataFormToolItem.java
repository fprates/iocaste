package org.iocaste.appbuilder.common.dataformtool;

import org.iocaste.appbuilder.common.AbstractComponentDataItem;
import org.iocaste.documents.common.DataElement;

public class DataFormToolItem extends AbstractComponentDataItem {
    public boolean secret;
    public DataElement element;
    
    public DataFormToolItem(DataFormToolData data, String name) {
        super(name);
    }
}
