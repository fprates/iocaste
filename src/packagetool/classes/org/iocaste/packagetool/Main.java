package org.iocaste.packagetool;

import org.iocaste.packagetool.common.InstallData;
import org.iocaste.protocol.Message;
import org.iocaste.shell.common.AbstractPage;
import org.iocaste.shell.common.PageContext;
import org.iocaste.shell.common.View;

public class Main extends AbstractPage {
    private Context context;
    
    public Main() {
        export("install", "install");
    }
    
    /**
     * 
     * @param view
     */
    public final void info() {
        Request.info(context);
    }

    @Override
    protected final PageContext init(View view) {
        context = new Context();
        context.function = this;
        Response.init(context);
        
        return context;
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
    public final void main() {
        Response.main(context);
    }
    
    /**
     * 
     * @param view
     */
    public final void packageinstall() {
        Request.packageInstall(context);
    }
    
    /**
     * 
     * @param view
     */
    public final void packageuninstall() {
        Request.packageUninstall(context);
    }
    
    /**
     * 
     * @param view
     */
    public final void printinfo() {
        Response.printInfo(context);
    }
}
