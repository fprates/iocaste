package org.iocaste.appbuilder.common.panel.dashboard;

import org.iocaste.appbuilder.common.dashboard.AbstractDashboardRenderer;
import org.iocaste.appbuilder.common.panel.AbstractPanelPage;
import org.iocaste.appbuilder.common.panel.Colors;
import org.iocaste.appbuilder.common.panel.PanelPageItem;
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
                    append(item.dashctx).
                    append("_container', '").
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
        PanelPageItem item;
        Container container;
        String style, linkname, htmlname;
        
        style = getStyle(name, OUTER).substring(1);
        
        container = new StandardContainer(
                parent, getContainerName(name, OUTER));
        container.setStyleClass(style);
        container.setVisible(false);
        
        linkname = null;
        for (String key : page.items.keySet()) {
            item = page.items.get(key);
            if (!item.dash.equals(name))
                continue;
            linkname = key;
            break;
        }
        
        htmlname = container.getHtmlName();
        container.addEvent("onMouseOut", new StringBuilder(
                "javascript:setClassStyle('").
                append(htmlname).append("', '").
                append(style).append("');setClassStyle('").
                append(linkname).
                append("_dbitem_link', 'std_dash_item')").toString());
        
        style = style.concat("_mouseover");
        container.addEvent("onMouseOver", new StringBuilder(
                "javascript:setClassStyle('").
                append(htmlname).append("', '").
                append(style).append("');setClassStyle('").
                append(linkname).
                append("_dbitem_link', 'std_dash_item_mouseover')").
                toString());

        style = getStyle(name, INNER).substring(1);
        container = new StandardContainer(
                container, getContainerName(name, INNER));
        container.setStyleClass(style);
    }

    @Override
    public void config() {
        String style, mouseover;
        
        for (String suffix : new String[] {
                "link",
                "active",
                "hover",
                "visited"
        }) {
            style = new StringBuilder(".std_dash_item:").
                    append(suffix).toString();
            stylesheet.newElement(style);
            stylesheet.put(style, "display", "block");
            stylesheet.put(style, "text-decoration", "none");
            stylesheet.put(style, "text-align", "center");
            stylesheet.put(style, "color", colors.get(Colors.DASH_FONT));
            
            mouseover = new StringBuilder(".std_dash_item_mouseover:").
                    append(suffix).toString();
            stylesheet.clone(mouseover, style);
            stylesheet.put(mouseover, "color", colors.get(Colors.FOCUS_FONT));
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
        stylesheet.put(style, "border-radius", "3px");
        stylesheet.put(style, "border-style", "solid");
        stylesheet.put(style, "border-color", colors.get(Colors.DASH_BORDER));
        stylesheet.put(style, "background-color", colors.get(Colors.DASH_BG));
        stylesheet.put(style, "font-size", "12pt");
        
        mouseover = style.concat("_mouseover");
        stylesheet.clone(mouseover, style);
        stylesheet.put(mouseover, "background-color", colors.get(Colors.FOCUS));
        
        style = getStyle(name, INNER);
        stylesheet.newElement(style);
        stylesheet.put(style, "margin", "auto");
        stylesheet.put(style, "padding", "1em");
    }
    
}