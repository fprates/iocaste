package org.iocaste.examples;

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
    
    public final void extern() {
        Extern.render(context.view);
    }
    
    public final void externsend() throws Exception {
        Extern.send(context);
    }
    
    public final PageContext init(View view) {
        context = new Context();
        
        return context;
    }
    
    public final InstallData install(Message message) {
        return Install.init();
    }
    
    public final void main() {
        Response.main(context.view);
    }
    
    public final void ping() {
        Response.ping(context.view);
    }
    
    public final void pinggo() {
        Request.pinggo(context);
    }
    
    public final void report() {
        Request.report(context);
    }
}

class Context extends PageContext { }