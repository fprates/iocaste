package org.iocaste.appbuilder.common.panel;

public class ActionRenderer extends StandardPanelContextRenderer {
    
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
        internal.suffix = "_dbaction_link";
        internal.cntstyle = "std_dash_action_cnt";
        internal.lnkstyle = "std_dash_action_lnk";
        internal.action = (group == null)? internal.dashname : group;
        add(internal);
    }

    @Override
    public void config() {
        Internal internal;
        
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
    }

    @Override
    public void config(String name) {
        String style;
        
        super.config(name);
        style = getStyle(name, OUTER);
        stylesheet.put(style, "display", "block");
    }

}
