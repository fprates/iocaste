package org.iocaste.appbuilder.common.dashboard;

import java.util.Map;

import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.Link;
import org.iocaste.shell.common.StandardContainer;

public class StandardDashboardRenderer extends AbstractDashboardRenderer {

    @Override
    public void add(
            String dashname, String name, Object value, int type, String text) {
        String linkname, action, group;
        Link link;
        
        group = entries.get(dashname).group;
        action = (group == null)? dashname : group;
        linkname = name.concat("_dbitem_link");
        
        link = new Link(getContainer(dashname, INNER), linkname, action);
        link.setStyleClass("db_dash_item");
        link.setText((text == null)? name : text);
        link.add(entries.get(action).choose, value, type);
    }
    
    @Override
    public void build(Container container, String name) {
        Container outer, inner;
        String innerstyle, outerstyle;
        
        outerstyle = getStyle(name, OUTER).substring(1);
        innerstyle = getStyle(name, INNER).substring(1);
        
        outer = new StandardContainer(container, entries.get(name).outercnt);
        outer.setStyleClass(outerstyle);
        outer.setVisible(false);
        
        inner = new StandardContainer(outer, entries.get(name).innercnt);
        inner.setStyleClass(innerstyle);
    }
    
    @Override
    public void config() {
        Map<String, String> style;
        
        style = stylesheet.get(factorystyle);
        style.put("float", "left");
        style.put("background-color", "#ffffff");
        style.put("width", "100%");
        
        style = stylesheet.newElement(".db_dash_item:link");
        style.put("display", "block");
        style.put("text-decoration", "none");
        style.put("padding", "3px");
        style.put("font-size", "12pt");
        style.put("color", "#0000ff");
        
        style = stylesheet.newElement(".db_dash_item:active");
        style.put("display", "block");
        style.put("text-decoration", "none");
        style.put("padding", "3px");
        style.put("font-size", "12pt");
        style.put("color", "#0000ff");
        
        style = stylesheet.newElement(".db_dash_item:hover");
        style.put("display", "block");
        style.put("text-decoration", "underline");
        style.put("padding", "3px");
        style.put("font-size", "12pt");
        style.put("color", "#0000ff");
        
        style = stylesheet.newElement(".db_dash_item:visited");
        style.put("display", "block");
        style.put("text-decoration", "none");
        style.put("padding", "3px");
        style.put("font-size", "12pt");
        style.put("color", "#0000ff");
    }
    
    @Override
    public void config(String name) {
        Entry entry;
        Map<String, String> style;
        
        entry = entries.get(name);
        style = stylesheet.newElement(entry.outerstyle);
        style.put("display", "inline;");
        style.put("border-width", "1px");
        style.put("border-style", "solid");
        style.put("float", "left");
        style.put("margin", "0.25em");
        
        style = stylesheet.newElement(entry.innerstyle);
        style.put("float", "left");
        style.put("padding", "1em");
        style.put("width", "100%");
    }
}
