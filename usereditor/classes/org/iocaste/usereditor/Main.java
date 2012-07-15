package org.iocaste.usereditor;

import org.iocaste.packagetool.common.InstallData;
import org.iocaste.protocol.Message;
import org.iocaste.shell.common.AbstractPage;
import org.iocaste.shell.common.View;

public class Main extends AbstractPage {
    public Main() {
        export("install", "install");
    }
    
    public final void addprofile(View view) {
        Request.addprofile(view);
    }
    
    public final void addtask(View view) {
        Request.addtask(view);
    }
    
    public final void create(View view) {
        Request.create(view, this);
    }
    
    public final void display(View view) {
        Request.load(view, this, Common.DISPLAY);
    }
    
    public final void form(View view) {
        Response.form(view, this);
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
        Request.save(view, this);
    }
    
    public final void update(View view) {
        Request.load(view, this, Common.UPDATE);
    }
}
