package org.iocaste.appbuilder.common.panel;

import org.iocaste.appbuilder.common.dashboard.AbstractDashboardRenderer;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.Link;
import org.iocaste.shell.common.StandardContainer;

public class StandardPanelContextRenderer extends AbstractDashboardRenderer {

    @Override
    public void add(String dashname, String name, Object value, int type) {
        Container container;
        String linkname, action, group;
        Link link;
        
        group = getGroup(dashname);
        action = (group == null)? dashname : group;
        linkname = name.concat("_dbitem_link");
        
        container = new StandardContainer(
                getContainer(dashname, INNER), linkname.concat("cnt"));
        container.setStyleClass("std_dash_context_cnt");
        
        container.addEvent("onMouseOut", new StringBuilder(
                "javascript:setClassStyle('").
                append(container.getHtmlName()).
                append("', 'std_dash_context_cnt')").toString());
        
        container.addEvent("onMouseOver", new StringBuilder(
                "javascript:setClassStyle('").
                append(container.getHtmlName()).
                append("', 'std_dash_context_cnt_mouseover')").toString());
        
        link = new Link(container, linkname, action);
        link.setStyleClass("std_dash_context_lnk");
        link.setText(name);
        link.add(getChoice(dashname), value, type);
    }

    @Override
    public void addText(String dashname, String name) {
        // TODO Stub de método gerado automaticamente
        
    }

    @Override
    public void build(Container parent, String name) {
        Container container;
        String style;
        
        style = getStyle(name, OUTER).substring(1);
        
        container = new StandardContainer(
                parent, getContainerName(name, OUTER));
        container.setStyleClass(style);
        container.setVisible(false);

        style = getStyle(name, INNER).substring(1);
        container = new StandardContainer(
                container, getContainerName(name, INNER));
        container.setStyleClass(style);
    }

    @Override
    public void config() {
        stylesheet.put(factorystyle, "float", "left");
        stylesheet.put(factorystyle, "height", "100%");
        stylesheet.put(factorystyle, "width", "100%");
        stylesheet.put(factorystyle, "display", "inline");
        
        for (String style : new String[] {
                ".std_dash_context_cnt",
                ".std_dash_context_lnk:link",
                ".std_dash_context_lnk:active",
                ".std_dash_context_lnk:hover",
                ".std_dash_context_lnk:visited"
        }) {
            stylesheet.newElement(style);
            
            switch (style) {
            case ".std_dash_context_cnt":
                stylesheet.put(style, "padding-top", "1em");
                stylesheet.put(style, "padding-bottom", "1em");
                stylesheet.put(style, "width", "100%");
                stylesheet.put(style, "display", "block");
                stylesheet.put(style, "border-bottom-style", "solid");
                stylesheet.put(style, "border-bottom-width", "2px");
                stylesheet.put(style, "border-bottom-color", Colors.BODY_BG);
                
                style = style.concat("_mouseover");
                stylesheet.clone(style, ".std_dash_context_cnt");
                stylesheet.put(style, "background-color", Colors.FOCUS);
                break;
            default:
                stylesheet.put(style, "width", "100%");
                stylesheet.put(style, "display", "block");
                stylesheet.put(style, "color", Colors.FONT);
                stylesheet.put(style, "text-decoration", "none");
                stylesheet.put(style, "text-align", "center");
                stylesheet.put(style, "font-size", "10pt");
                break;
            }
        }
    }

    @Override
    public void config(String name) {
        String style;
        
        style = getStyle(name, OUTER);
        stylesheet.newElement(style);
        stylesheet.put(style, "float", "left");
        stylesheet.put(style, "width", "100%");
        stylesheet.put(style, "display", "none");
        
        style = getStyle(name, INNER);
        stylesheet.newElement(style);
    }
    
}