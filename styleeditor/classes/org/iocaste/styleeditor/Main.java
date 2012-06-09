package org.iocaste.styleeditor;

import org.iocaste.packagetool.common.InstallData;
import org.iocaste.protocol.Message;
import org.iocaste.shell.common.AbstractPage;
import org.iocaste.shell.common.View;

public class Main extends AbstractPage {

    public Main() {
        export("install", "install");
    }
    
    public final void addstyle(View view) {
        Request.addstyle(view);
    }
    
    public final void create(View view) throws Exception {
        Request.create(view, this);
    }
    
    public final InstallData install(Message message) {
        InstallData data = new InstallData();
        
        data.link("STYLE", "iocaste-styleeditor");
        
        return data;
    }
    
    public final void main(View view) throws Exception {
        Response.selection(view, this);
    }
    
    public final void show(View view) throws Exception {
        Request.load(view, this, Common.SHOW);
    }
    
    public final void style(View view) throws Exception {
        Response.style(view, this);
    }
    
    public final void update(View view) throws Exception {
        Request.load(view, this, Common.UPDATE);
    }
}
