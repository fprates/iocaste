package org.iocaste.usereditor;

import org.iocaste.shell.common.ViewData;

public class Common {
    public static final byte CREATE = 0;
    public static final byte DISPLAY = 1;
    public static final byte UPDATE = 2;
    public static final String[] TITLE = {
        "usereditor-create",
        "usereditor-display",
        "usereditor-update"
    };
    
    public static final byte getMode(ViewData view) {
        return view.getParameter("mode");
    }
}
