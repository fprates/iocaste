package org.iocaste.gconfigview;

import org.iocaste.packagetool.common.InstallData;
import org.iocaste.protocol.Message;
import org.iocaste.shell.common.AbstractPage;
import org.iocaste.shell.common.View;

/**
 * 
 * @author francisco.prates
 *
 */
public class Main extends AbstractPage {

    public Main() {
        export("install", "install");
    }
    
    /**
     * 
     * @param view
     */
    public final void display(View view) {
        Request.display(view);
    }
    
    /**
     * 
     * @param view
     */
    public final void edit(View view) {
        Request.edit(view);
    }
    
    /**
     * 
     * @param message
     * @return
     */
    public final InstallData install(Message message) {
        return Install.init();
    }
    
    /**
     * 
     * @param view
     */
    public final void main(View view) {
        Response.main(view, this);
    }
}
