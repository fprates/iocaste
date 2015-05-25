package org.iocaste.appbuilder.common.panel.dashboard;

import java.util.Map;

import org.iocaste.appbuilder.common.dashboard.AbstractDashboardRenderer;
import org.iocaste.appbuilder.common.panel.AbstractPanelPage;
import org.iocaste.appbuilder.common.panel.PanelPageItem;
import org.iocaste.appbuilder.common.style.CommonStyle;
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
        Map<String, String> style;
        String name, mouseover;
        CommonStyle profile;

        profile = CommonStyle.get();
        
        style = stylesheet.get(factorystyle);
        style.put("margin", profile.dashboard.margin);
        style.put("width", profile.dashboard.width);
        style.put("height", profile.dashboard.height);
        
        for (String suffix : new String[] {
                "link",
                "active",
                "hover",
                "visited"
        }) {
            name = new StringBuilder(".std_dash_item:").
                    append(suffix).toString();
            style = stylesheet.newElement(name);
            style.put("display", "block");
            style.put("text-decoration", "none");
            style.put("text-align", "center");
            style.put("color", profile.dashboard.font.color);
            
            mouseover = new StringBuilder(".std_dash_item_mouseover:").
                    append(suffix).toString();
            stylesheet.clone(mouseover, name).put(
                    "color", profile.dashboard.font.focuscolor);
        }
    }

    @Override
    public void config(String name) {
        Map<String, String> style;
        String mouseover;
        CommonStyle profile;
        
        profile = CommonStyle.get();
        style = stylesheet.newElement(getStyle(name, OUTER));
        style.put("width", "15em");
        style.put("height", "3em");
        style.put("float", "left");
        style.put("margin", "0.5em");
        style.put("padding", "1em");
        style.put("display", "inline");
        style.put("background-color", profile.dashboard.itembgcolor);
        style.put("border-radius", "3px");
        style.put("border-style", "solid");
        style.put("border-color", profile.dashboard.border.all.color);
        style.put("font-size", profile.dashboard.font.size);
        
        mouseover = getStyle(name, OUTER).concat("_mouseover");
        stylesheet.clone(mouseover, getStyle(name, OUTER)).put(
                "background-color", profile.dashboard.focusbgcolor);
        
        style = stylesheet.newElement(getStyle(name, INNER));
        style.put("margin", "auto");
        style.put("padding", "1em");
    }
    
}