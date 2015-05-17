package org.iocaste.appbuilder.common.panel.context;

import org.iocaste.appbuilder.common.panel.Colors;
import org.iocaste.shell.common.Button;

public class ActionRenderer extends StandardPanelContextRenderer {
    
    @Override
    public void add(String dashname, String name, Object value, int type) {
        String group;
        Internal internal;
        Button button;
        String style;
        
        if ((value != null) && value.equals("submit")) {
            style = "std_dash_action_submit";
            
            button = new Button(getContainer(dashname, INNER), name);
            button.setSubmit(true);
            button.setStyleClass(style);
            addEvents(button, style);
            return;
        }
        
        group = getGroup(dashname);
        
        internal = new Internal();
        internal.name = name;
        internal.dashname = dashname;
        internal.value = value;
        internal.type = type;
        internal.suffix = "_dbaction_link";
        internal.cntstyle = "std_dash_action_cnt";
        internal.lnkstyle = "std_dash_action_lnk";
        internal.action = (group == null)? internal.dashname : group;
        internal.submit = true;
        add(internal);
    }

    @Override
    public void config() {
        Internal internal;
        String style;
        
        internal = new Internal();
        internal.cntstyle = ".std_dash_action_cnt";
        internal.bgcolor = colors.get(Colors.ACTION_BG);
        internal.linksstyles = new String[] {
                ".std_dash_action_cnt",
                ".std_dash_action_lnk:link",
                ".std_dash_action_lnk:active",
                ".std_dash_action_lnk:hover",
                ".std_dash_action_lnk:visited"
        };
        config(internal);
        
        style = ".std_dash_action_submit";
        stylesheet.clone(style, ".std_dash_action_cnt");
        stylesheet.put(style, "color", colors.get(Colors.FONT));
        stylesheet.put(style, "border-top-style", "none");
        stylesheet.put(style, "border-left-style", "none");
        stylesheet.put(style, "border-right-style", "none");
        stylesheet.put(style, "font-family", "\"Verdana\", \"sans-serif\"");
        stylesheet.put(style, "font-size", "10pt");
        
        style = style.concat("_mouseover");
        stylesheet.clone(style, ".std_dash_action_submit");
        stylesheet.put(style,
                "background-color", colors.get(Colors.FOCUS));
    }

    @Override
    public void config(String name) {
        String style;
        
        super.config(name);
        style = getStyle(name, OUTER);
        stylesheet.put(style, "display", "block");
    }

}
