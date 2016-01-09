package org.iocaste.appbuilder.common.cmodelviewer;

import java.util.Map;

import org.iocaste.appbuilder.common.AbstractActionHandler;
import org.iocaste.appbuilder.common.ComponentEntry;
import org.iocaste.appbuilder.common.PageBuilderContext;

public class InputValidate extends AbstractActionHandler {

    @Override
    protected void execute(PageBuilderContext context) {
        refresh(context);
    }
    
    protected void refresh(PageBuilderContext context) {
        Context extcontext;
        Map<String, ComponentEntry> entries;
        ComponentEntry entry;
        
        extcontext = getExtendedContext();
        entries = context.getView().getComponents().entries;
        for (String key : entries.keySet()) {
            entry = entries.get(key);
            switch (entry.data.type) {
            case DATA_FORM:
                extcontext.dataforms.put(key, getdf(key));
                break;
            case TABLE_TOOL:
                extcontext.set(key, tableitemsget(key));
                break;
            default:
                break;
            }
        }
    }
}