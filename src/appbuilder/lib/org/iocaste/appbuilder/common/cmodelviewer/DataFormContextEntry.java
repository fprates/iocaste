package org.iocaste.appbuilder.common.cmodelviewer;

import org.iocaste.appbuilder.common.ContextDataHandler;
import org.iocaste.appbuilder.common.ContextEntry;
import org.iocaste.appbuilder.common.ViewSpecItem;
import org.iocaste.documents.common.ExtendedObject;

public class DataFormContextEntry implements ContextEntry {
    public ExtendedObject object;
    public ContextDataHandler handler;
    
    @Override
    public final ViewSpecItem.TYPES getType() {
        return ViewSpecItem.TYPES.DATA_FORM;
    }
}
