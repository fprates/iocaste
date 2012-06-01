package org.iocaste.usereditor;

import org.iocaste.packagetool.common.InstallData;
import org.iocaste.protocol.Message;
import org.iocaste.shell.common.AbstractPage;
import org.iocaste.shell.common.ViewData;

public class Main extends AbstractPage {
    public Main() {
        export("install", "install");
    }
    
    public final void create(ViewData view) throws Exception {
        Request.create(view, this);
    }
    
    public final void display(ViewData view) throws Exception {
        Request.load(view, this, Common.DISPLAY);
    }
    
    public final void form(ViewData view) throws Exception {
        Response.form(view, this);
    }
    
    public final InstallData install(Message message) {
        return Install.init();
    }
    
    public final void main(ViewData view) throws Exception {
        Response.selector(view, this);
    }
    
    public final void save(ViewData view) throws Exception {
        Request.save(view, this);
    }
    
    public final void update(ViewData view) throws Exception {
        Request.load(view, this, Common.UPDATE);
    }
}
