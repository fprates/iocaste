package org.iocaste.appbuilder.common.panel.context;

import java.util.Map;

import org.iocaste.appbuilder.common.style.CommonStyle;
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
        Map<String, String> style;
        CommonStyle profile;
        
        profile = CommonStyle.get();
        internal = new Internal();
        internal.cntstyle = ".std_dash_action_cnt";
        internal.bgcolor = profile.context.actionbgcolor;
        internal.linksstyles = new String[] {
                ".std_dash_action_cnt",
                ".std_dash_action_lnk:link",
                ".std_dash_action_lnk:active",
                ".std_dash_action_lnk:hover",
                ".std_dash_action_lnk:visited"
        };
        config(internal);
        
        style = stylesheet.clone(
                ".std_dash_action_submit", ".std_dash_action_cnt");
        style.put("color", profile.context.font.color);
        style.put("border-top-style", "none");
        style.put("border-left-style", "none");
        style.put("border-right-style", "none");
        style.put("font-family", "\"Verdana\", \"sans-serif\"");
        style.put("font-size", "10pt");
        
        style = stylesheet.clone(
                ".std_dash_action_submit_mouseover", ".std_dash_action_submit");
        style.put("background-color", profile.context.focusbgcolor);
    }

    @Override
    public void config(String name) {
        Map<String, String> style;
        
        super.config(name);
        style = stylesheet.get(getStyle(name, OUTER));
        style.put("display", "block");
    }

}
