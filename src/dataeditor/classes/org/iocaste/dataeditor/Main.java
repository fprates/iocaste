package org.iocaste.dataeditor;

import org.iocaste.documents.common.Documents;
import org.iocaste.packagetool.common.InstallData;
import org.iocaste.protocol.Message;
import org.iocaste.shell.common.AbstractPage;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.AbstractContext;
import org.iocaste.shell.common.View;

public class Main extends AbstractPage {
    private Context context;
    
    public Main() {
        export("install", "install");
    }
    
    public final void display() {
        DataForm form = context.view.getElement("model");
        String modelname = form.get("NAME").get();
        String message = Request.load(modelname, context);
        
        if (message != null) {
            context.view.message(Const.ERROR, message);
            return;
        }
        
        context.mode = Context.DISPLAY;
        setReloadableView(true);
        redirect("output");
    }
    
    public final void form() {
        Response.form(context);
    }
    
    @Override
    public final AbstractContext init(View view) {
        context = new Context();
        context.modelmodel = new Documents(this).getModel("MODEL");
        
        return context;
    }
    
    public final void insert() {
        setReloadableView(true);
        redirect("form");
    }
    
    /**
     * 
     */
    public final void insertcancel() {
        back();
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
        String message, model;
        String action = getParameter("action");
        if (action == null) {
            Response.main(context);
            return;
        }
        
        model = getParameter("model");
        message = Request.load(model, context);
        if (message != null) {
            Response.main(context);
            return;
        }
        
        switch (action) {
        case "edit":
            context.mode = Context.UPDATE;
            Response.output(context);
            break;
        case "display":
            context.mode = Context.DISPLAY;
            Response.output(context);
            break;
        default:
            Response.main(context);
            break;
        }
    }
    
    public final void output() {
        Response.output(context);
    }
    
    public final void save() {
        Request.save(context);
    }
    
    public final void update() {
        DataForm form = context.view.getElement("model");
        String modelname = form.get("NAME").get();
        String message = Request.load(modelname, context);
        
        if (message != null) {
            context.view.message(Const.ERROR, message);
            return;
        }
        
        context.mode = Context.UPDATE;
        setReloadableView(true);
        redirect("output");
    }
}
