package org.iocaste.appbuilder.common.dashboard;

import java.util.Map;

import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.panel.Colors;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.InputComponent;

public interface DashboardRenderer {
    public static final byte INNER = 0;
    public static final byte OUTER = 1;
    
    public abstract void add(
            String dashname, String name, Object value, int type);
    
    public abstract void addText(String dashname, String name);
    
    public abstract void build(Container container, String name);
    
    public abstract void config();
    
    public abstract void config(String name);
    
    public abstract void entryInstance(String group, String name);
    
    public abstract Container getContainer(String name, byte type);
    
    public abstract String getStyle(String name, byte type);

    public abstract InputComponent getInput(String name);
    
    public abstract void setColors(Map<Colors, String> colors);
    
    public abstract void setContainerName(String name);
    
    public abstract void setContext(PageBuilderContext context);

    public abstract void setStyle(String name);

    public abstract void setStyleProperty(String name, String value);
    
    public abstract void setStyleProperty(
            String style, String name, String value);
    
    public abstract void setVisible(String name, boolean visible);
}
