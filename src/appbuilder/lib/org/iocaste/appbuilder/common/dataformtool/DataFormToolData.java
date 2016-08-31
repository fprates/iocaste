package org.iocaste.appbuilder.common.dataformtool;

import org.iocaste.appbuilder.common.AbstractComponentData;
import org.iocaste.appbuilder.common.AbstractComponentDataItem;
import org.iocaste.appbuilder.common.ViewSpecItem.TYPES;
import org.iocaste.documents.common.ExtendedObject;

public class DataFormToolData extends AbstractComponentData {
    public DataFormToolItem nsitem;
    public ExtendedObject object;
    public String sh;
    public String[] groups;
    public boolean internallabel;
    
    public DataFormToolData() {
        super(TYPES.DATA_FORM);
    }
    
    @SuppressWarnings("unchecked")
    public final <T extends AbstractComponentDataItem> T instance(String name) {
        DataFormToolItem item;
        
        item = get(name);
        if (item == null) {
            item = new DataFormToolItem(this, name);
            item.name = name;
            if (name != null)
                put(name, item);
        }
        return (T)item;
    }
    
    public final DataFormToolItem nsItemInstance() {
        return (nsitem == null)? nsitem = instance(null) : nsitem;
    }
}
