package org.iocaste.packagetool;

import java.util.Map;

import org.iocaste.packagetool.common.InstallData;
import org.iocaste.protocol.Message;
import org.iocaste.shell.common.AbstractPage;
import org.iocaste.shell.common.View;

public class Main extends AbstractPage {
    private Map<String, String> pkgsdata;
    
    public Main() {
        export("install", "install");
    }
    
    /**
     * 
     * @param view
     */
    public final void info(View view) {
        Request.info(view, this);
    }

    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.AbstractPage#init(
     *     org.iocaste.shell.common.View)
     */
    @Override
    protected final void init(View view) {
        pkgsdata = Response.init(this);
    }
    
    /**
     * 
     * @param message
     * @return
     */
    public final InstallData install(Message message) {
        return Install.init(this);
    }
    
    /**
     * 
     * @param view
     */
    public final void main(View view) {
        Response.main(view, pkgsdata);
    }
    
    /**
     * 
     * @param view
     */
    public final void packageinstall(View view) {
        Request.packageInstall(view, this);
    }
    
    /**
     * 
     * @param view
     */
    public final void packageuninstall(View view) {
        Request.packageUninstall(view, this);
    }
    
    /**
     * 
     * @param view
     */
    public final void printinfo(View view) {
        Response.printInfo(view);
    }
}
