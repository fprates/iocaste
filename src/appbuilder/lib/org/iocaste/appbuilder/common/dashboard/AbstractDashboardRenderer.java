package org.iocaste.appbuilder.common.dashboard;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.InputComponent;
import org.iocaste.shell.common.StyleSheet;

public abstract class AbstractDashboardRenderer implements DashboardRenderer {
    protected PageBuilderContext context;
    protected StyleSheet stylesheet;
    protected Map<String, Entry> entries;
    protected String factorystyle, container;
    
    public AbstractDashboardRenderer() {
        entries = new HashMap<>();
    }
    
    @Override
    public final void entryInstance(String group, String name) {
        Entry entry;
        
        entries.put(name, entry = new Entry());
        entry.choose = name.concat("_dbitem_choose");
        entry.outercnt = name.concat("_container");
        entry.innercnt = name.concat("_inner");
        entry.outerstyle = ".db_dash_".concat(name);
        entry.innerstyle = ".db_dash_inner_".concat(name);
        entry.group = group;
    }
    
    protected final String getChoice(String name) {
        return entries.get(name).choose;
    }
    
    @Override
    public final Container getContainer(String name, byte type) {
        return context.view.getElement(getContainerName(name, type));
    }
    
    protected final String getContainerName() {
        return container;
    }
    
    protected final String getContainerName(String name, byte type) {
        switch (type) {
        case INNER:
            return entries.get(name).innercnt;
        default:
            return entries.get(name).outercnt;
        }
    }
    
    protected final String getGroup(String name) {
        return entries.get(name).group;
    }
    
    @Override
    public final InputComponent getInput(String name) {
        return context.view.getElement(entries.get(name).choose);
    }
    
    @Override
    public final String getStyle(String name, byte type) {
        switch (type) {
        case INNER:
            return entries.get(name).innerstyle;
        default:
            return entries.get(name).outerstyle;
        }
    }
    
    @Override
    public final void setContainerName(String name) {
        this.container = name;
    }
    
    @Override
    public final void setContext(PageBuilderContext context) {
        this.context = context;
        stylesheet = context.view.styleSheetInstance();
    }
    
    @Override
    public final void setStyle(String name) {
        if (factorystyle != null)
            stylesheet.remove(factorystyle);
        
        factorystyle = name;
        stylesheet.newElement(factorystyle);
    }

    public final void setStyle(String name, byte type, String style) {
        switch (type) {
        case INNER:
            entries.get(name).innerstyle = style;
            break;
        case OUTER:
            entries.get(name).outerstyle = style;
            break;
        }
    }
    
    @Override
    public final void setStyleProperty(String name, String value) {
        stylesheet.get(factorystyle).put(name, value);
    }
    
    @Override
    public final void setStyleProperty(String style, String name, String value)
    {
        stylesheet.get(style).put(name, value);
    }
    
    @Override
    public final void setVisible(String name, boolean visible) {
        entries.get(name).visible = visible;
    }
}

class Entry {
    String outercnt, innercnt, outerstyle, innerstyle, choose, group;
    boolean visible;
}