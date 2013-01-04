package org.iocaste.usereditor;

import org.iocaste.documents.common.Documents;
import org.iocaste.packagetool.common.InstallData;
import org.iocaste.protocol.Message;
import org.iocaste.shell.common.AbstractPage;
import org.iocaste.shell.common.View;

public class Main extends AbstractPage {
    private Context context;
    
    public Main() {
        export("install", "install");
    }
    
    public final void acceptprofiles(View view) {
        context.profileshelper.accept(view);
    }
    
    public final void accepttasks(View view) {
        context.taskshelper.accept(view);
    }
    
    public final void addprofiles(View view) {
        context.profileshelper.add(view);
    }
    
    public final void addtasks(View view) {
        context.taskshelper.add(view);
    }
    
    public final void create(View view) {
        context.mode = Context.CREATE;
        context.userdata = Request.create(view, this);
    }
    
    public final void display(View view) {
        context.mode = Context.DISPLAY;
        context.userdata = Request.load(view, this);
    }
    
    public final void form(View view) {
        Response.form(view, context);
    }
    
    public final void init(View view) {
        Documents documents;
        
        if (!view.getPageName().equals("main"))
            return;
        
        context = new Context();
        context.function = this;
        
        documents = new Documents(this);
        context.tasksmodel = documents.getModel("USER_TASKS_GROUPS");
        context.profilesmodel = documents.getModel("USER_AUTHORITY");
        context.usermodel = documents.getModel("LOGIN");
    }
    
    public final InstallData install(Message message) {
        return Install.init();
    }
    
    public final void main(View view) {
        Response.selector(view, this);
    }
    
    public final void removeprofiles(View view) {
        context.profileshelper.remove(view);
    }
    
    public final void removetasks(View view) {
        context.taskshelper.remove(view);
    }
    
    public final void save(View view) {
        Request.save(view, context);
    }
    
    public final void update(View view) {
        context.mode = Context.UPDATE;
        context.userdata = Request.load(view, this);
    }
}
