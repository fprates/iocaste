package org.iocaste.appbuilder.common.panel;

import java.util.Map;

import org.iocaste.appbuilder.common.AbstractViewInput;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.shell.common.PageStackItem;

public class StandardPanelInput extends AbstractViewInput {
    private AbstractPanelPage page;
    private Map<String, PageStackItem> positions;
    
    public StandardPanelInput(AbstractPanelPage page) {
        this.page = page;
    }
    
    @Override
    protected void execute(PageBuilderContext context) {
        String title;
        PanelPageItem item;
        PageStackItem position;

        if (positions.size() > 0)
            for (String name : positions.keySet()) {
                position = positions.get(name);
                title = position.getTitle();
                if (title == null)
                    title = name;
                dbitemadd("navigation", name, title, name);
            }
        
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
    
    public final void setPositions(Map<String, PageStackItem> positions) {
        this.positions = positions;
    }
}
