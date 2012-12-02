package org.iocaste.styleeditor;

import org.iocaste.packagetool.common.InstallData;
import org.iocaste.protocol.Message;
import org.iocaste.shell.common.AbstractPage;
import org.iocaste.shell.common.View;

public class Main extends AbstractPage {
    private Context context;
    
    public Main() {
        context = new Context();
        export("install", "install");
    }
    
    public final void addstyle(View view) {
        Request.addstyle(view);
    }
    
    public final void create(View view) throws Exception {
        context.mode = Context.CREATE;
        Request.create(view, this);
    }
    
    public final InstallData install(Message message) {
        return Install.init();
    }
    
    public final void main(View view) throws Exception {
        Response.selection(view, this);
    }
    
    public final void show(View view) throws Exception {
        context.mode = Context.SHOW;
        Request.load(view, this);
    }
    
    public final void style(View view) throws Exception {
        Response.style(view, this, context);
    }
    
    public final void update(View view) throws Exception {
        context.mode = Context.UPDATE;
        context.elements = Request.load(view, this);
    }
}
