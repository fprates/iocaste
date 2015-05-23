package org.iocaste.tasksel;

import java.util.Map;

import org.iocaste.appbuilder.common.dashboard.StandardDashboardRenderer;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.ExpandBar;
import org.iocaste.shell.common.StandardContainer;

public class TaskRenderer extends StandardDashboardRenderer {

    @Override
    public void config() {
        Map<String, String> style;
        
        super.config();
        style = stylesheet.newElement(".tasksel_edge");
        style.put("color", "#ffffff");
        style.put("background-color", "#000000");
        style.put("display", "block");
        style.put("width", "100%");
        style.put("border-width", "1px");
        style.put("border-color", "#ffffff");
        style.put("border-style", "solid");
    }
    
    @Override
    public final void build(Container container, String name) {
        ExpandBar outer;
        String innerstyle, outerstyle;
        
        outerstyle = getStyle(name, OUTER).substring(1);
        innerstyle = getStyle(name, INNER).substring(1);
        
        outer = new ExpandBar(container, getContainerName(name, OUTER));
        outer.setText(name);
        outer.setEdgeStyle("tasksel_edge");
        outer.setStyleClass(outerstyle);
        outer.setInternalStyle(innerstyle);
        outer.setEnabled(false);
        
        new StandardContainer(outer, getContainerName(name, INNER));
    }
    
    @Override
    public void config(String name) {
        Map<String, String> style;
        String outerstyle, innerstyle;

        outerstyle = getStyle(name, OUTER);
        style = stylesheet.newElement(outerstyle);
        style.put("margin", "0.25em");
        style.put("display", "inline");
        style.put("width", "32%");
        style.put("border-width", "1px");
        style.put("border-style", "solid");
        style.put("float", "left");

        innerstyle = getStyle(name, INNER);
        style = stylesheet.newElement(innerstyle);
        style.put("padding", "1em");
        style.put("width", "100%");
        style.put("float", "left");
    }

}
