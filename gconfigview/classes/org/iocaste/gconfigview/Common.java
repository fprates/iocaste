package org.iocaste.gconfigview;

import org.iocaste.shell.common.View;

public class Common {
    public static final byte DISPLAY = 0;
    public static final byte EDIT = 1;
    public static final byte SELECT = 2;
    public static final String[] TITLES = {
        "config.display",
        "config.edit",
        "config.select"
    };
    
    public static final byte getMode(View view) {
        return view.getParameter("mode");
    }
}
