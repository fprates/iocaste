package org.iocaste.appbuilder.common.panel;

import org.iocaste.appbuilder.common.AbstractActionHandler;
import org.iocaste.appbuilder.common.AbstractViewConfig;
import org.iocaste.appbuilder.common.AbstractViewInput;
import org.iocaste.appbuilder.common.AbstractViewSpec;
import org.iocaste.appbuilder.common.ExtendedContext;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.ViewContext;
import org.iocaste.appbuilder.common.navcontrol.NavControl;
import org.iocaste.shell.common.StyleSheet;

public class StandardPanel {
    private PageBuilderContext context;
    
    public StandardPanel(PageBuilderContext context) {
        this.context = context;
    }

    public final void instance(
            String name, AbstractPanelPage page, ExtendedContext extcontext) {
        PanelPageItem item;
        PanelPageItemContextEntry entry;
        ViewContext view;
        
        view = context.instance(name);
        view.set(new StandardPanelSpec(page));
        view.set(new StandardPanelConfig(page));
        view.set(new StandardPanelInput(page));
        view.set(extcontext);
        view.setUpdate(true);
        
        page.setContext(context);
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

class StandardPanelSpec extends AbstractViewSpec {
    private AbstractPanelPage page;
    
    public StandardPanelSpec(AbstractPanelPage page) {
        this.page = page;
    }

    @Override
    protected final void execute() {
        PanelPageItem item;
        
        form("main");
        navcontrol("main");
        
        standardcontainer("main", "context");
        dashboard("context", "navigation");
        dashboard("context", "dashcontext");
        
        standardcontainer("main", "content");
        dashboard("content", "dashitems");
        for (String name : page.items.keySet()) {
            item = page.items.get(name);
            item.dash = name.concat("_page");
            item.dashctx = item.dash.concat("ctx");
            dashboarditem("dashitems", item.dash);
            dashboardgroup("dashcontext", item.dashctx);
        }
    }
}

class StandardPanelConfig extends AbstractViewConfig {
    private AbstractPanelPage page;
    
    public StandardPanelConfig(AbstractPanelPage page) {
        this.page = page;
    }
    
    @Override
    protected void execute(PageBuilderContext context) {
        String style;
        StyleSheet stylesheet;
        NavControl navcontrol;
        
        style = ".form_content";
        stylesheet = context.view.styleSheetInstance();
        stylesheet.put(style, "height", "100%");
        stylesheet.put(style, "background-color", Colors.BODY_BG);

        
        style = ".std_panel_context";
        stylesheet.newElement(style);
        stylesheet.put(style, "top", "70px");
        stylesheet.put(style, "left", "0px");
        stylesheet.put(style, "width", "20em");
        stylesheet.put(style, "height", "100%");
        stylesheet.put(style, "float", "left");
        stylesheet.put(style, "display", "inline");
        stylesheet.put(style, "position", "fixed");
        stylesheet.put(style, "background-color", Colors.COMPONENT_BG);
        stylesheet.put(style, "font-size", "12pt");
        stylesheet.put(style, "border-bottom-style", "solid");
        stylesheet.put(style, "border-bottom-width", "2px");
        stylesheet.put(style, "border-bottom-color", Colors.BODY_BG);
        getElement("context").setStyleClass(style.substring(1));
        
        style = ".std_panel_content";
        stylesheet.newElement(style);
        stylesheet.put(style, "top", "70px");
        stylesheet.put(style, "right", "20em");
        stylesheet.put(style, "left", "20em");
        stylesheet.put(style, "display", "inline");
        stylesheet.put(style, "float", "right");
        stylesheet.put(style, "height", "100%");
        stylesheet.put(style, "width", "60em");
        stylesheet.put(style, "position", "fixed");
        stylesheet.put(style, "margin", "auto");
        stylesheet.put(style, "padding", "0px");
        stylesheet.put(style, "font-size", "12pt");
        getElement("content").setStyleClass(style.substring(1));
        
        navcontrol = getNavControl();
        navcontrol.setDesign(new StandardPanelDesign());
        
        getDashboard("dashitems").setRenderer(
                new StandardPanelItemsRenderer(page));
        
        getDashboard("dashcontext").setRenderer(
                new StandardPanelContextRenderer());
    }
}

class StandardPanelInput extends AbstractViewInput {
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
