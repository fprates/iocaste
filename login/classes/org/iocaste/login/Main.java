package org.iocaste.login;

import org.iocaste.shell.common.AbstractPage;
import org.iocaste.shell.common.View;

public class Main extends AbstractPage {
    
    /**
     * 
     * @param view
     */
    public final void authentic(View view) {
        Response.authentic(view);
    }
    
    /**
     * 
     * @param view
     */
    public final void connect(View view) {
        Request.connect(view, this);
    }
}
