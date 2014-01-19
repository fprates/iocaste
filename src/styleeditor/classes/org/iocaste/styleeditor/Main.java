package org.iocaste.styleeditor;

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
    
    public final void create() {
        context.mode = Context.CREATE;
        Request.create(context);
    }
    
    public final void detail() {
        Response.detail(context);
    }
    
    public final void element() {
        Request.element(context);
    }
    
    @Override
    public final PageContext init(View view) {
        context = new Context();
        
        return context;
    }
    
    public final InstallData install(Message message) {
        return Install.init();
    }
    
    public final void main() {
        Response.selection(context);
    }
    
    public final void show() {
        context.mode = Context.SHOW;
        context.elements = Request.load(context);
    }
    
    public final void style() {
        Response.style(context);
    }
    
    public final void update() {
        context.mode = Context.UPDATE;
        context.elements = Request.load(context);
    }
}
