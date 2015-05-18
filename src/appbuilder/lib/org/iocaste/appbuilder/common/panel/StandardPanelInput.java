package org.iocaste.appbuilder.common.panel;

import java.util.Map;
import java.util.Set;

import org.iocaste.appbuilder.common.AbstractViewInput;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.panel.context.PanelPageEntryType;
import org.iocaste.appbuilder.common.panel.context.PanelPageItemContextEntry;
import org.iocaste.shell.common.PageStackItem;

public class StandardPanelInput extends AbstractViewInput {
    private AbstractPanelPage page;
    private Map<String, PageStackItem> positions;
    
    public StandardPanelInput(AbstractPanelPage page) {
        this.page = page;
    }
    
    @Override
    protected void execute(PageBuilderContext context) {
        AbstractViewInput input;
        
        input = page.getInput();
        if (input != null)
            input.run(context, false);
    }

    @Override
    protected void init(PageBuilderContext context) {
        AbstractViewInput input;
        String title, submit, destination;
        PanelPageItem item;
        PageStackItem position;
        PanelPageItemContextEntry ctxitem, ctxitemi;
        Set<String> entrieskeys;
        
        if (positions.size() > 0)
            for (String name : positions.keySet()) {
                position = positions.get(name);
                title = position.getTitle();
                if (title == null)
                    title = name;
                dbitemadd("navigation", name, title, name);
            }
        
        for (String action : page.getActions())
            dbitemadd("actions", action, action);
        
        submit = page.getSubmit();
        if (submit != null)
            dbitemadd("actions", submit, submit, "submit");
        
        for (String name : page.items.keySet()) {
            item = page.items.get(name);
            if (item.dashboard)
                dbitemadd("dashitems", item.dash, item.name);
            
            entrieskeys = item.context.entries.keySet();
            destination = (item.dashboard)? "dashcontext" : "actions";
            for (String text : entrieskeys) {
                ctxitem = item.context.entries.get(text);
                switch (ctxitem.type) {
                case GROUP:
                    dbtextadd(destination, item.dashctx, ctxitem.group);
                    
                    for (String texti : entrieskeys) {
                        ctxitemi = item.context.entries.get(texti);
                        if ((ctxitemi.group == null) ||
                                (ctxitemi.type == PanelPageEntryType.GROUP) ||
                                !ctxitem.group.equals(ctxitemi.group))
                            continue;

                        dbitemadd(destination, item.dashctx, texti,
                                ctxitemi.task);
                    }
                    break;
                default:
                    if (ctxitem.group != null)
                        break;
                    
                    dbitemadd(destination, item.dashctx, text, ctxitem.task);
                    break;
                }
            }
        }
        
        input = page.getInput();
        if (input != null)
            input.run(context, true);
    }
    
    public final void setPositions(Map<String, PageStackItem> positions) {
        this.positions = positions;
    }
}
