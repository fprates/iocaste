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
            ctxentry = extcontext.tilesInstance(name);
            switch (entry.data.type) {
            case TILES:
                tilesset(name, ctxentry.getObjects());
                break;
            case DATA_FORM:
                dfset(name, ctxentry.getObject());
                break;
            case TABLE_TOOL:
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
