package org.iocaste.install;

import org.iocaste.shell.common.View;

public class DBConfig {
    public static final byte KEEP_BASE = 0;
    public static final byte CHANGE_BASE = 1;
    public static final byte NEW_BASE = 2;
    
    /**
     * 
     * @param view
     * @throws Exception
     */
    public static final void action(View view) throws Exception {
        DBConfigRequest.action(view);
    }
    
    /**
     * 
     * @param view
     */
    public static final void render(View view) {
        DBConfigResponse.render(view);
    }
}
