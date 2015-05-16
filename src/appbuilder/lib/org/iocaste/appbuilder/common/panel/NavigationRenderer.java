package org.iocaste.appbuilder.common.panel;

public class NavigationRenderer extends StandardPanelContextRenderer {

    @Override
    public void add(String dashname, String name, Object value, int type) {
        Internal internal;
        
        internal = new Internal();
        internal.name = internal.action = name;
        internal.dashname = dashname;
        internal.value = value;
        internal.type = type;
        internal.cntstyle = "std_dash_navigation_cnt";
        internal.lnkstyle = "std_dash_navigation_lnk";
        internal.cancellable = true;
        add(internal);
    }
    
    @Override
    public void config() {
        Internal internal;
        
        internal = new Internal();
        internal.cntstyle = ".std_dash_navigation_cnt";
        internal.linksstyles = new String[] {
                ".std_dash_navigation_cnt",
                ".std_dash_navigation_lnk:link",
                ".std_dash_navigation_lnk:active",
                ".std_dash_navigation_lnk:hover",
                ".std_dash_navigation_lnk:visited"
        };
        config(internal);
    }
}
