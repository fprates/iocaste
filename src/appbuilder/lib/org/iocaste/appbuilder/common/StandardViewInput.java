package org.iocaste.appbuilder.common;

import java.util.Map;

public class StandardViewInput extends AbstractViewInput {

    @Override
    protected void execute(PageBuilderContext context) {
        ContextEntry ctxentry;
        ComponentEntry entry;
        Map<String, ComponentEntry> entries;
        ExtendedContext extcontext = getExtendedContext();
        
        entries = context.getView().getComponents().entries;
        for (String name : entries.keySet()) {
            entry = entries.get(name);
            switch (entry.data.type) {
            case TILES:
                ctxentry = extcontext.tilesInstance(name);
                tilesset(name, ctxentry.getObjects());
                break;
            case DATA_FORM:
                ctxentry = extcontext.dataformInstance(name);
                dfset(name, ctxentry.getObject());
                break;
            case TABLE_TOOL:
                ctxentry = extcontext.tableInstance(name);
                tableitemsset(name, ctxentry.getObjects());
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
