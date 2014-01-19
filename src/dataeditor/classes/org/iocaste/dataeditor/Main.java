package org.iocaste.dataeditor;

import org.iocaste.documents.common.Documents;
import org.iocaste.packagetool.common.InstallData;
import org.iocaste.protocol.Message;
import org.iocaste.shell.common.AbstractPage;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.PageContext;
import org.iocaste.shell.common.TableItem;
import org.iocaste.shell.common.View;

public class Main extends AbstractPage {
    private Context context;
    
    public Main() {
        export("install", "install");
    }
    
    public final void acceptitens() {
        context.tablehelper.accept();
    }
    
    public final void additens() {
        context.tablehelper.add();
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
        context.view.redirect("itens");
    }
    
    public final void form() {
        Response.form(context);
    }
    
    @Override
    public final PageContext init(View view) {
        context = new Context();
        context.modelmodel = new Documents(this).getModel("MODEL");
        
        return context;
    }
    
    public final void insert() {
        Request.insert(context.view);
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
    
    public final void itens() {
        Response.itens(context);
    }
    
    public final void main() {
        Response.main(context);
    }

    public final void removeitens() {
        for (TableItem item : context.tablehelper.getItems())
            if (item.isSelected())
                context.deleted.add(item);
        context.tablehelper.remove();
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
        context.view.redirect("itens");
    }
}
