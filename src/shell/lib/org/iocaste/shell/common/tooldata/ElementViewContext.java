package org.iocaste.shell.common.tooldata;

import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.View;
import org.iocaste.shell.common.tooldata.ViewSpecItem.TYPES;

public class ElementViewContext implements Context {
    private View view;
    private ToolData tooldata;
    
    public ElementViewContext(
            View view, Container container, TYPES type, String name) {
        tooldata = new ToolData(type, name);
        if (container != null) {
            this.view = container.getView();
            tooldata.parent = container.getHtmlName();
        } else {
            this.view = view;
        }
    }

    public ElementViewContext(
            ToolData tooldata, Container container, TYPES type) {
        this.tooldata = tooldata;
        this.view = container.getView();
        tooldata.parent = container.getHtmlName();
    }
    
    @Override
    public ToolData get(String name) {
        return tooldata;
    }

    @Override
    public View getView() {
        return view;
    }
}
