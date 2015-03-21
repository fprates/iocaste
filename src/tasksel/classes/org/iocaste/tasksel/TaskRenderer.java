package org.iocaste.tasksel;

import org.iocaste.appbuilder.common.dashboard.StandardDashboardRenderer;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.ExpandBar;
import org.iocaste.shell.common.StandardContainer;

public class TaskRenderer extends StandardDashboardRenderer {

    @Override
    public void config() {
        super.config();
        stylesheet.newElement(".tasksel_edge");
        stylesheet.put(".tasksel_edge", "color", "#ffffff");
        stylesheet.put(".tasksel_edge", "background-color", "#000000");
        stylesheet.put(".tasksel_edge", "display", "block");
        stylesheet.put(".tasksel_edge", "width", "100%");
        stylesheet.put(".tasksel_edge", "border-width", "1px");
        stylesheet.put(".tasksel_edge", "border-color", "#ffffff");
        stylesheet.put(".tasksel_edge", "border-style", "solid");
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
        outer.setEnabled(true);
        
        new StandardContainer(outer, getContainerName(name, INNER));
    }
    
    @Override
    public void config(String name) {
        String outerstyle, innerstyle;

        outerstyle = getStyle(name, OUTER);
        stylesheet.newElement(outerstyle);
        stylesheet.put(outerstyle, "margin", "0.25em");
        stylesheet.put(outerstyle, "display", "inline");
        stylesheet.put(outerstyle, "width", "32%");
        stylesheet.put(outerstyle, "border-width", "1px");
        stylesheet.put(outerstyle, "border-style", "solid");
        stylesheet.put(outerstyle, "float", "left");

        innerstyle = getStyle(name, INNER);
        stylesheet.newElement(innerstyle);
        stylesheet.put(innerstyle, "padding", "1em");
        stylesheet.put(innerstyle, "width", "100%");
        stylesheet.put(innerstyle, "float", "left");
    }

}
