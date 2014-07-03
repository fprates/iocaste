package org.iocaste.usereditor;

import org.iocaste.documents.common.Documents;
import org.iocaste.packagetool.common.InstallData;
import org.iocaste.protocol.Message;
import org.iocaste.shell.common.AbstractPage;
import org.iocaste.shell.common.AbstractContext;
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
    
    public final void delete() {
        Request.delete(context);
    }
    
    public final void display() {
        context.mode = Context.DISPLAY;
        context.userdata = Request.load(context);
    }
    
    public final void form() {
        Response.form(context);
    }
    
    @Override
    public final AbstractContext init(View view) {
        Documents documents = new Documents(this);
        
        context = new Context();
        context.tasksmodel = documents.getModel("USER_TASKS_GROUPS");
        context.tasksmodel.getModelItem("GROUP").
                setSearchHelp("SH_TASKS_GROUPS");
        context.profilesmodel = documents.getModel("USER_AUTHORITY");
        context.profilesmodel.getModelItem("PROFILE").
                setSearchHelp("SH_USER_PROFILE");
        context.usermodel = documents.getModel("LOGIN");
        
        return context;
    }
    
    public final InstallData install(Message message) {
        return Install.init();
    }
    
    public final void main() {
        Response.selector(context);
    }
    
    public final void save() {
        Request.save(context);
    }
    
    public final void update() {
        context.mode = Context.UPDATE;
        context.userdata = Request.load(context);
    }
}
