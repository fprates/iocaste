package org.iocaste.appbuilder.common.panel;

import java.util.LinkedHashMap;
import java.util.Map;

import org.iocaste.appbuilder.common.AbstractActionHandler;
import org.iocaste.appbuilder.common.ExtendedContext;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.ViewContext;
import org.iocaste.shell.common.PageStackItem;

public class StandardPanel {
    private PageBuilderContext context;
    
    public StandardPanel(PageBuilderContext context) {
        this.context = context;
    }

    public final void instance(
            String name, AbstractPanelPage page, ExtendedContext extcontext) {
        StandardPanelSpec spec;
        StandardPanelInput input;
        PanelPageItem item;
        PanelPageItemContextEntry entry;
        ViewContext view;
        Map<String, PageStackItem> positions;
        
        positions = new LinkedHashMap<>();

        spec = new StandardPanelSpec(page);
        spec.setPositions(positions);
        
        input = new StandardPanelInput(page);
        input.setPositions(positions);
        
        view = context.instance(name);
        view.set(spec);
        view.set(new StandardPanelConfig(page));
        view.set(input);
        view.set(extcontext);
        view.setUpdate(true);
        
        page.setViewContext(view);
        page.execute();
        
        for (String key : page.items.keySet()) {
            item = page.items.get(key);
            for (String entrykey : item.context.entries.keySet()) {
                entry = item.context.entries.get(entrykey);
                switch (entry.type) {
                case TASK:
                    view.put(key.concat("_pagectx"),
                            new TaskCall(item, TaskCall.CONTEXT));
                    break;
                }
            }
        }
    }
}

class TaskCall extends AbstractActionHandler {
    public static final int PANEL = 0;
    public static final int CONTEXT = 1;
    private PanelPageItem item;
    private int source;
    
    public TaskCall(PanelPageItem item, int source) {
        this.item = item;
        this.source = source;
    }
    
    @Override
    protected void execute(PageBuilderContext context) throws Exception {
        String task;
        
        switch (source) {
        case PANEL:
            task = dbactiongetst("dashitems", item.dash);
            break;
        default:
            task = dbactiongetst("dashcontext", item.dashctx);
            break;
        }
        
        taskredirect(task);
    }
    
}
