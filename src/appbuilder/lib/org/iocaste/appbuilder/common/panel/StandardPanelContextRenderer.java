package org.iocaste.appbuilder.common.panel;

import org.iocaste.appbuilder.common.dashboard.AbstractDashboardRenderer;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.Link;
import org.iocaste.shell.common.StandardContainer;
import org.iocaste.shell.common.Text;

public class StandardPanelContextRenderer extends AbstractDashboardRenderer {
    
    @Override
    public void add(String dashname, String name, Object value, int type) {
        Internal internal;
        
        internal = new Internal();
        internal.name = name;
        internal.dashname = dashname;
        internal.value = value;
        internal.type = type;
        internal.suffix = "_dbitem_link";
        internal.cntstyle = "std_dash_context_cnt";
        internal.lnkstyle = "std_dash_context_lnk";
        add(internal);
    }
    
    protected final void add(Internal internal) {
        Container container;
        String linkname, action, group;
        Link link;
        
        group = getGroup(internal.dashname);
        action = (group == null)? internal.dashname : group;
        linkname = internal.name.concat(internal.suffix);
        
        container = new StandardContainer(
                getContainer(internal.dashname, INNER), linkname.concat("cnt"));
        container.setStyleClass(internal.cntstyle);
        
        container.addEvent("onMouseOut", new StringBuilder(
                "javascript:setClassStyle('").
                append(container.getHtmlName()).append("', '").
                append(internal.cntstyle).append("')").toString());
        
        container.addEvent("onMouseOver", new StringBuilder(
                "javascript:setClassStyle('").
                append(container.getHtmlName()).append("', '").
                append(internal.cntstyle).append("_mouseover')").toString());
        
        link = new Link(container, linkname, action);
        link.setStyleClass(internal.lnkstyle);
        link.setText(internal.name);
        link.add(
                getChoice(internal.dashname), internal.value, internal.type);
        link.setCancellable(internal.cancellable);
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
        String style;
        
        style = getStyle(name, OUTER);
        stylesheet.newElement(style);
        stylesheet.put(style, "float", "left");
        stylesheet.put(style, "width", "100%");
        stylesheet.put(style, "display", "none");
        
        style = getStyle(name, INNER);
        stylesheet.newElement(style);
    }
    
    protected void config(Internal internal) {
        String style_;
        
        stylesheet.put(factorystyle, "float", "left");
        stylesheet.put(factorystyle, "width", "100%");
        stylesheet.put(factorystyle, "display", "inline");
        
        for (String style : internal.linksstyles) {
            stylesheet.newElement(style);
            
            if (style.equals(internal.cntstyle)) {
                stylesheet.put(style, "padding-top", "1em");
                stylesheet.put(style, "padding-bottom", "1em");
                stylesheet.put(style, "width", "100%");
                stylesheet.put(style, "display", "block");
                stylesheet.put(style, "border-bottom-style", "solid");
                stylesheet.put(style, "border-bottom-width", "2px");
                stylesheet.put(style,
                        "border-bottom-color", colors.get(Colors.BODY_BG));
                
                style = style.concat("_mouseover");
                stylesheet.clone(style, internal.cntstyle);
                stylesheet.put(style,
                        "background-color", colors.get(Colors.FOCUS));
            } else {
                stylesheet.put(style, "width", "100%");
                stylesheet.put(style, "display", "block");
                stylesheet.put(style,
                        "color", colors.get(Colors.FONT));
                stylesheet.put(style, "text-decoration", "none");
                stylesheet.put(style, "text-align", "center");
                stylesheet.put(style, "font-size", "10pt");
            }
        }
        
        style_ = ".std_dash_context_txt";
        stylesheet.newElement(style_);
        stylesheet.put(style_, "color", colors.get(Colors.FONT));
        stylesheet.put(style_, "text-align", "center");
        stylesheet.put(style_, "font-size", "10pt");
        stylesheet.put(style_, "font-weight", "bold");
        stylesheet.put(style_, "margin", "0px");
        
        style_ = ".std_dash_context_grp";
        stylesheet.newElement(style_);
        stylesheet.put(style_, "padding-top", "0.5em");
        stylesheet.put(style_, "padding-bottom", "0.5em");
        stylesheet.put(style_, "width", "100%");
        stylesheet.put(style_, "display", "block");
        stylesheet.put(style_, "background-color", colors.get(Colors.GROUP_BG));
        stylesheet.put(style_, "border-bottom-style", "solid");
        stylesheet.put(style_, "border-bottom-width", "2px");
        stylesheet.put(style_,
                "border-bottom-color", colors.get(Colors.BODY_BG));
    }
}

class Internal {;
    public String name, dashname, suffix, cntstyle, lnkstyle;
    public Object value;
    public int type;
    public boolean cancellable;
    public String[] linksstyles;
}