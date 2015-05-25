package org.iocaste.appbuilder.common.panel;

import java.util.Set;

import org.iocaste.appbuilder.common.AbstractViewInput;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.panel.context.PanelPageEntryType;
import org.iocaste.appbuilder.common.panel.context.PanelPageItemContextEntry;

public class StandardPanelInput extends AbstractViewInput {
    private AbstractPanelPage page;
    
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
        String submit, destination;
        PanelPageItem item;
        PanelPageItemContextEntry ctxitem, ctxitemi;
        Set<String> entrieskeys;
        
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
}
