package org.iocaste.appbuilder.common.panel;

import org.iocaste.appbuilder.common.dashboard.AbstractDashboardRenderer;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.Link;
import org.iocaste.shell.common.StandardContainer;

public class StandardPanelItemsRenderer extends AbstractDashboardRenderer {
    private AbstractPanelPage page;
    
    public StandardPanelItemsRenderer(AbstractPanelPage page) {
        this.page = page;
    }
    
    @Override
    public void add(String dashname, String name, Object value, int type) {
        PanelPageItem item;
        StringBuilder sb;
        String linkname, action;
        Link link;
        
        linkname = name.concat("_dbitem_link");
        sb = new StringBuilder("javascript:");
        for (String key : page.items.keySet()) {
            item = page.items.get(key);
            sb.append("setElementDisplay('").
                    append(item.dashctx).append("_container', '").
                    append(item.dash.equals(dashname)? "inline');" : "none');");
        }
        
        action = sb.toString();
        link = new Link(getContainer(dashname, INNER), linkname, action);
        link.setStyleClass("std_dash_item");
        link.setText(name);
        link.add(getChoice(dashname), value, type);
        link.setAbsolute(true);
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
        
        container.addEvent("onMouseOut", new StringBuilder(
                "javascript:setClassStyle('").
                append(container.getHtmlName()).append("', '").
                append(style).append("')").toString());
        
        style = style.concat("_mouseover");
        container.addEvent("onMouseOver", new StringBuilder(
                "javascript:setClassStyle('").
                append(container.getHtmlName()).append("', '").
                append(style).append("')").toString());

        style = getStyle(name, INNER).substring(1);
        container = new StandardContainer(
                container, getContainerName(name, INNER));
        container.setStyleClass(style);
    }

    @Override
    public void config() {
        stylesheet.put(factorystyle, "top", "20em");
        stylesheet.put(factorystyle, "bottom", "20em");
        stylesheet.put(factorystyle, "width", "55em");
        stylesheet.put(factorystyle, "margin", "auto");
        stylesheet.put(factorystyle, "position", "fixed");
        stylesheet.put(factorystyle, "background-color", Colors.BODY_BG);
        
        for (String style : new String[] {
                ".std_dash_item:link",
                ".std_dash_item:active",
                ".std_dash_item:hover",
                ".std_dash_item:visited"
        }) {
            stylesheet.newElement(style);
            stylesheet.put(style, "display", "block");
            stylesheet.put(style, "color", Colors.FONT);
            stylesheet.put(style, "text-decoration", "none");
            stylesheet.put(style, "text-align", "center");
        }
    }

    @Override
    public void config(String name) {
        String style, mouseover;
        
        style = getStyle(name, OUTER);
        stylesheet.newElement(style);
        stylesheet.put(style, "width", "15em");
        stylesheet.put(style, "height", "3em");
        stylesheet.put(style, "float", "left");
        stylesheet.put(style, "margin", "0.5em");
        stylesheet.put(style, "padding", "1em");
        stylesheet.put(style, "display", "inline");
        stylesheet.put(style, "color", Colors.FOCUS);
        stylesheet.put(style, "background-color", Colors.DASH_BG);
        stylesheet.put(style, "font-size", "12pt");
        
        mouseover = style.concat("_mouseover");
        stylesheet.clone(mouseover, style);
        stylesheet.put(mouseover, "background-color", Colors.FOCUS);
        
        style = getStyle(name, INNER);
        stylesheet.newElement(style);
        stylesheet.put(style, "margin", "auto");
        stylesheet.put(style, "padding", "1em");
    }
    
}