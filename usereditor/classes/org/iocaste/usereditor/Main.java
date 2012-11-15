package org.iocaste.usereditor;

import org.iocaste.packagetool.common.InstallData;
import org.iocaste.protocol.Message;
import org.iocaste.shell.common.AbstractPage;
import org.iocaste.shell.common.View;

public class Main extends AbstractPage {
    private byte mode;
    private UserData userdata;
    
    public Main() {
        export("install", "install");
    }
    
    public final void addprofile(View view) {
        Request.addprofile(view, mode);
    }
    
    public final void addtask(View view) {
        Request.addtask(view, mode);
    }
    
    public final void create(View view) {
        mode = Common.CREATE;
        userdata = Request.create(view, this);
    }
    
    public final void display(View view) {
        mode = Common.DISPLAY;
        userdata = Request.load(view, this);
    }
    
    public final void form(View view) {
        Response.form(view, this, userdata, mode);
    }
    
    public final InstallData install(Message message) {
        return Install.init();
    }
    
    public final void main(View view) {
        Response.selector(view, this);
    }
    
    public final void removeprofile(View view) {
        Request.removeprofile(view);
    }
    
    public final void removetask(View view) {
        Request.removetask(view);
    }
    
    public final void save(View view) {
        Request.save(view, this, mode);
    }
    
    public final void update(View view) {
        mode = Common.UPDATE;
        userdata = Request.load(view, this);
    }
}
