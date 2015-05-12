package org.iocaste.appbuilder.common.panel;

import org.iocaste.appbuilder.common.AbstractViewInput;
import org.iocaste.appbuilder.common.PageBuilderContext;

public class StandardPanelInput extends AbstractViewInput {
    private AbstractPanelPage page;
    
    public StandardPanelInput(AbstractPanelPage page) {
        this.page = page;
    }
    
    @Override
    protected void execute(PageBuilderContext context) {
        PanelPageItem item;
        
        for (String name : page.items.keySet()) {
            item = page.items.get(name);
            dbitemadd("dashitems", item.dash, item.name);
            for (String text : item.context.entries.keySet())
                dbitemadd("dashcontext", item.dashctx, text,
                        item.context.entries.get(text).task);
        }
    }

    @Override
    protected void init(PageBuilderContext context) {
        execute(context);
    }
}
