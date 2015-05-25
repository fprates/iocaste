package org.iocaste.appbuilder.common.panel.context;

import java.util.Map;

import org.iocaste.appbuilder.common.dashboard.AbstractDashboardRenderer;
import org.iocaste.appbuilder.common.style.CommonStyle;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.Element;
import org.iocaste.shell.common.Link;
import org.iocaste.shell.common.StandardContainer;
import org.iocaste.shell.common.Text;

public class StandardPanelContextRenderer extends AbstractDashboardRenderer {
    
    @Override
    public void add(String dashname, String name, Object value, int type) {
        String group;
        Internal internal;
        
        group = getGroup(dashname);
        
        internal = new Internal();
        internal.name = name;
        internal.dashname = dashname;
        internal.value = value;
        internal.type = type;
        internal.suffix = "_dbitem_link";
        internal.cntstyle = "std_dash_context_cnt";
        internal.lnkstyle = "std_dash_context_lnk";
        internal.action = (group == null)? internal.dashname : group;
        add(internal);
    }
    
    protected final void add(Internal internal) {
        Container container;
        String linkname;
        Link link;
        
        if (internal.suffix != null)
            linkname = internal.name.concat(internal.suffix);
        else
            linkname = internal.name;
        
        container = new StandardContainer(
                getContainer(internal.dashname, INNER), linkname.concat("cnt"));
        container.setStyleClass(internal.cntstyle);
        addEvents(container, internal.cntstyle);
        
        link = new Link(container, linkname, internal.action);
        link.setText((internal.text == null)? internal.name : internal.text);
        link.add(getChoice(internal.dashname), internal.value, internal.type);
        link.setStyleClass(internal.lnkstyle);
        link.setCancellable(internal.cancellable);
    }
    
    protected final void addEvents(Element element, String style) {
        element.addEvent("onMouseOut", new StringBuilder(
                "javascript:setClassStyle('").
                append(element.getHtmlName()).append("', '").
                append(style).append("')").toString());
        
        element.addEvent("onMouseOver", new StringBuilder(
                "javascript:setClassStyle('").
                append(element.getHtmlName()).append("', '").
                append(style).append("_mouseover')").toString());
    }
    
    @Override
    public void addText(String dashname, String name) {
        Internal internal;
        
        internal = new Internal();
        internal.name = name;
        internal.dashname = dashname;
        internal.cntstyle = "std_dash_context_grp";
        internal.lnkstyle = "std_dash_context_txt";
        internal.suffix = "_item";
        addText(internal);
    }
    
    protected final void addText(Internal internal) {
        Container container;
        String name;
        Text text;

        name = internal.name.concat(internal.suffix);
        container = new StandardContainer(
                getContainer(internal.dashname, INNER), name.concat("cnt"));
        container.setStyleClass(internal.cntstyle);
        
        text = new Text(container, name);
        text.setStyleClass(internal.lnkstyle);
        text.setText(internal.name);
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
        Internal internal;
        
        internal = new Internal();
        internal.cntstyle = ".std_dash_context_cnt";
        internal.linksstyles = new String[] {
                ".std_dash_context_cnt",
                ".std_dash_context_lnk:link",
                ".std_dash_context_lnk:active",
                ".std_dash_context_lnk:hover",
                ".std_dash_context_lnk:visited"
        };
        config(internal);
    }

    @Override
    public void config(String name) {
        Map<String, String> style;
        
        style = stylesheet.newElement(getStyle(name, OUTER));
        style.put("float", "left");
        style.put("width", "100%");
        style.put("display", "none");
        
        stylesheet.newElement(getStyle(name, INNER));
    }
    
    protected void config(Internal internal) {
        Map<String, String> style;
        CommonStyle profile;
        
        profile = CommonStyle.get();
        style = stylesheet.get(factorystyle);
        style.put("float", "left");
        style.put("width", "100%");
        style.put("display", "inline");
        
        for (String linkstyle : internal.linksstyles) {
            style = stylesheet.newElement(linkstyle);
            
            if (linkstyle.equals(internal.cntstyle)) {
                style.put("padding-top", "1em");
                style.put("padding-bottom", "1em");
                style.put("width", "100%");
                style.put("display", "block");
                style.put("border-bottom-style", "solid");
                style.put("border-bottom-width", "2px");
                style.put("border-bottom-color", profile.context.shade);
                if (internal.bgcolor != null)
                    style.put("background-color", internal.bgcolor);
                
                linkstyle = linkstyle.concat("_mouseover");
                stylesheet.clone(linkstyle, internal.cntstyle).put(
                        "background-color", profile.context.focusbgcolor);
            } else {
                style.put("width", "100%");
                style.put("display", "block");
                style.put("color", profile.context.font.color);
                style.put("text-decoration", "none");
                style.put("text-align", "center");
                style.put("font-size", profile.context.font.size);
                style.put("font-family", profile.context.font.family);
            }
        }
        
        style = stylesheet.newElement(".std_dash_context_txt");
        style.put("color", profile.context.font.color);
        style.put("text-align", "center");
        style.put("font-size", profile.context.font.size);
        style.put("font-family", profile.context.font.family);
        style.put("font-weight", "bold");
        style.put("margin", "0px");
        
        style = stylesheet.newElement(".std_dash_context_grp");
        style.put("padding-top", "0.5em");
        style.put("padding-bottom", "0.5em");
        style.put("width", "100%");
        style.put("display", "block");
        style.put("background-color", profile.context.groupbgcolor);
        style.put("border-bottom-style", "solid");
        style.put("border-bottom-width", "2px");
        style.put("border-bottom-color", profile.context.shade);
    }
}

class Internal {;
    public String name, dashname, suffix, cntstyle, lnkstyle, action, bgcolor;
    public String text;
    public Object value;
    public int type;
    public boolean cancellable, submit;
    public String[] linksstyles;
}