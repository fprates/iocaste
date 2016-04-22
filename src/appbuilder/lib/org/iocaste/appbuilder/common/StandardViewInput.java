package org.iocaste.appbuilder.common;

import org.iocaste.appbuilder.common.cmodelviewer.TableToolContextEntry;

public class StandardViewInput extends AbstractViewInput {

    @Override
    protected void execute(PageBuilderContext context) {
        TableToolContextEntry ttentry;
        ExtendedContext extcontext = getExtendedContext();
        PageContext page = extcontext.getPageContext();
        
        for (String name : page.dataforms.keySet())
            dfset(name, page.dataforms.get(name));
        
        for (String name : page.tabletools.keySet()) {
            ttentry = page.tabletools.get(name);
            if (ttentry.handler != null) {
                ttentry.handler.setExtendedContext(extcontext);
                tableitemsset(name, ttentry.handler.input(name));
            } else {
                tableitemsset(name, ttentry.items.values());
            }
        }
    }

    @Override
    protected void init(PageBuilderContext context) {
        execute(context);
    }
}
