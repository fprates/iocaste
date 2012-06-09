package org.iocaste.styleeditor;

import org.iocaste.shell.common.View;

public class Common {
    public static final byte CREATE = 0;
    public static final byte SHOW = 1;
    public static final byte UPDATE = 2;
    
    public static final byte getMode(View view) {
        return view.getParameter("mode");
    }
}
