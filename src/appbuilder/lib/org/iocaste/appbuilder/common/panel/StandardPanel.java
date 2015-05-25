package org.iocaste.appbuilder.common.panel;

import java.util.LinkedHashMap;
import java.util.Map;

import org.iocaste.appbuilder.common.AbstractActionHandler;
import org.iocaste.appbuilder.common.ExtendedContext;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.ViewContext;
import org.iocaste.appbuilder.common.panel.context.PanelPageEntryType;
import org.iocaste.appbuilder.common.panel.context.PanelPageItemContextEntry;
import org.iocaste.appbuilder.common.style.CommonStyle;
import org.iocaste.shell.common.PageStackItem;

public class StandardPanel {
    private PageBuilderContext context;
    
    public StandardPanel(PageBuilderContext context) {
        this.context = context;
        CommonStyle.style = "default";
        CommonStyle.instance("default");
    }

    public final void instance(
            String name, AbstractPanelPage page, ExtendedContext extcontext) {
        StandardPanelSpec spec;
        StandardPanelInput input;
        PanelPageItem item;
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
        
        page.setViewContext(view);
        page.execute();
        
        for (String key : page.items.keySet()) {
            item = page.items.get(key);
            prepareContextTasks(view, key, item);
        }
    }
    
    private final void prepareContextTasks(
            ViewContext view, String key, PanelPageItem item) {
        PanelPageItemContextEntry entry;
        TaskCall taskcall;
        String source;
        
        source = (item.dashboard)? "dashcontext" : "actions";
        taskcall = new TaskCall(source, item);
        for (String entrykey : item.context.entries.keySet()) {
            entry = item.context.entries.get(entrykey);
            if (!entry.type.equals(PanelPageEntryType.TASK))
                continue;
            item.dashctx = key.concat("_pagectx");
            view.put(item.dashctx, taskcall);
        }
    }
}

class TaskCall extends AbstractActionHandler {
    public static final int PANEL = 0;
    public static final int CONTEXT = 1;
    private PanelPageItem item;
    private String source;
    
    public TaskCall(String source, PanelPageItem item) {
        this.item = item;
        this.source = source;
    }
    
    @Override
    protected void execute(PageBuilderContext context) throws Exception {
        String task;

        task = dbactiongetst(source, item.dashctx);
        taskredirect(task);
    }
    
}
