package org.iocaste.appbuilder.common.panel;

import org.iocaste.appbuilder.common.AbstractActionHandler;
import org.iocaste.appbuilder.common.ExtendedContext;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.ViewContext;

public class StandardPanel {
    private PageBuilderContext context;
    
    public StandardPanel(PageBuilderContext context) {
        this.context = context;
    }

    public final void instance(
            String name, AbstractPanelPage page, ExtendedContext extcontext) {
        ViewContext view;
        
        view = context.instance(name);
        view.set(new StandardPanelSpec(page));
        view.set(new StandardPanelConfig(page));
        view.set(new StandardPanelInput(page));
        view.set(extcontext);
        
        page.setViewContext(view);
        page.execute();
        
        extcontext.pageInstance(name);
        
        reassignActions(view, page);
    }
    
    public static final void reassignActions(
            ViewContext view, AbstractPanelPage page) {
//        PanelPageItem item;
//        String source;
//        
//        for (String key : page.items.keySet()) {
//            item = page.items.get(key);
//            
//            source = (item.dashboard)? "dashcontext" : "actions";
//            item.dashctx = key.concat("_pagectx");
//            view.put(item.dashctx, new TaskCall(source, item.dashctx));
//        }
    }
}

class TaskCall extends AbstractActionHandler {
    public static final int PANEL = 0;
    public static final int CONTEXT = 1;
    private String source, dashctx;
    
    public TaskCall(String source, String dashctx) {
        this.dashctx = dashctx;
        this.source = source;
    }
    
    @Override
    protected void execute(PageBuilderContext context) throws Exception {
        String task;

        task = dbactiongetst(source, dashctx);
        taskredirect(task);
    }
    
}
