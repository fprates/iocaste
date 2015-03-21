package org.iocaste.appbuilder.common.dashboard;

import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.Link;
import org.iocaste.shell.common.StandardContainer;
import org.iocaste.shell.common.Text;

public class StandardDashboardRenderer extends AbstractDashboardRenderer {

    @Override
    public void add(String dashname, String name, Object value, int type)
    {
        String linkname;
        Link link;
        
        linkname = name.concat("_dbitem_link");
        link = new Link(getContainer(dashname, INNER), linkname, dashname);
        link.setStyleClass("db_dash_item");
        link.setText(name);
        link.add(entries.get(dashname).choose, value, type);
    }
    
    public final void addText(String dashname, String name) {
        String textname;
        Text text;
        
        textname = name.concat("_item");
        text = new Text(getContainer(dashname, INNER), textname);
        text.setText(name);
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
        stylesheet.newElement(factorystyle);
        stylesheet.put(factorystyle, "float", "left");
        stylesheet.put(factorystyle, "background-color", "#ffffff");
        stylesheet.put(factorystyle, "width", "100%");
        
        stylesheet.newElement(".db_dash_item:link");
        stylesheet.put(".db_dash_item:link", "display", "block");
        stylesheet.put(".db_dash_item:link", "text-decoration", "none");
        stylesheet.put(".db_dash_item:link", "padding", "3px");
        stylesheet.put(".db_dash_item:link", "font-size", "12pt");
        stylesheet.put(".db_dash_item:link", "color", "#0000ff");
        
        stylesheet.newElement(".db_dash_item:active");
        stylesheet.put(".db_dash_item:active", "display", "block");
        stylesheet.put(".db_dash_item:active", "text-decoration", "none");
        stylesheet.put(".db_dash_item:active", "padding", "3px");
        stylesheet.put(".db_dash_item:active", "font-size", "12pt");
        stylesheet.put(".db_dash_item:active", "color", "#0000ff");
        
        stylesheet.newElement(".db_dash_item:hover");
        stylesheet.put(".db_dash_item:hover", "display", "block");
        stylesheet.put(".db_dash_item:hover", "text-decoration", "underline");
        stylesheet.put(".db_dash_item:hover", "padding", "3px");
        stylesheet.put(".db_dash_item:hover", "font-size", "12pt");
        stylesheet.put(".db_dash_item:hover", "color", "#0000ff");
        
        stylesheet.newElement(".db_dash_item:visited");
        stylesheet.put(".db_dash_item:visited", "display", "block");
        stylesheet.put(".db_dash_item:visited", "text-decoration", "none");
        stylesheet.put(".db_dash_item:visited", "padding", "3px");
        stylesheet.put(".db_dash_item:visited", "font-size", "12pt");
        stylesheet.put(".db_dash_item:visited", "color", "#0000ff");
    }
    
    @Override
    public void config(String name) {
        Entry entry;

        entries.put(name, entry = new Entry());
        entry.choose = name.concat("_dbitem_choose");
        entry.outercnt = name.concat("_container");
        entry.innercnt = name.concat("_inner");
        entry.outerstyle = ".db_dash_".concat(name);
        entry.innerstyle = ".db_dash_inner_".concat(name);
        
        stylesheet.newElement(entry.outerstyle);
        stylesheet.put(entry.outerstyle, "display", "inline;");
        stylesheet.put(entry.outerstyle, "border-width", "1px");
        stylesheet.put(entry.outerstyle, "border-style", "solid");
        stylesheet.put(entry.outerstyle, "float", "left");
        stylesheet.put(entry.outerstyle, "margin", "0.25em");
        
        stylesheet.newElement(entry.innerstyle);
        stylesheet.put(entry.innerstyle, "float", "left");
        stylesheet.put(entry.innerstyle, "padding", "1em");
        stylesheet.put(entry.innerstyle, "width", "100%");
    }
}
