package org.iocaste.appbuilder.common;

import java.util.Map;

import org.iocaste.appbuilder.common.cmodelviewer.TableToolContextEntry;

public class StandardViewInput extends AbstractViewInput {

    @Override
    protected void execute(PageBuilderContext context) {
        TableToolContextEntry ttentry;
        ComponentEntry entry;
        Map<String, ComponentEntry> entries;
        ExtendedContext extcontext = getExtendedContext();
        
        entries = context.getView().getComponents().entries;
        for (String name : entries.keySet()) {
            entry = entries.get(name);
            switch (entry.data.type) {
            case DATA_FORM:
                dfset(name, extcontext.dfobjectget(name));
                break;
            case TABLE_TOOL:
                ttentry = extcontext.tableInstance(name);
                if (ttentry.handler != null) {
                    ttentry.handler.setExtendedContext(extcontext);
                    tableitemsset(name, ttentry.handler.input(name));
                } else {
                    tableitemsset(name, ttentry.items.values());
                }
                break;
            default:
                break;
            }
        }
    }

    @Override
    protected void init(PageBuilderContext context) {
        execute(context);
    }
}
