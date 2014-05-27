package org.iocaste.gconfigview;

import org.iocaste.packagetool.common.InstallData;
import org.iocaste.protocol.Message;
import org.iocaste.shell.common.AbstractPage;
import org.iocaste.shell.common.AbstractContext;
import org.iocaste.shell.common.View;

/**
 * 
 * @author francisco.prates
 *
 */
public class Main extends AbstractPage {
    private Context context;
    
    public Main() {
        export("install", "install");
    }
    
    public final void configform() {
        Response.configform(context);
    }
    
    public final void display() {
        context.mode = Context.DISPLAY;
        Request.load(context);
    }
    
    public final void edit() {
        context.mode = Context.EDIT;
        Request.load(context);
    }
    
    @Override
    public final AbstractContext init(View view) {
        context = new Context();
        
        return context;
    }
    
    /**
     * 
     * @param message
     * @return
     */
    public final InstallData install(Message message) {
        return Install.init();
    }
    
    public final void main() {
        Response.main(context);
    }
    
    public final void save() {
        Request.save(context);
    }
}
