package org.iocaste.appbuilder.common.cmodelviewer;

import org.iocaste.appbuilder.common.AbstractContextEntry;
import org.iocaste.appbuilder.common.ContextDataHandler;
import org.iocaste.appbuilder.common.ViewSpecItem;

public class DataFormContextEntry extends AbstractContextEntry {
    public ContextDataHandler handler;
    
    public DataFormContextEntry() {
        super(ViewSpecItem.TYPES.DATA_FORM);
    }
}
