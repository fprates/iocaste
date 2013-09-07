package org.iocaste.dataeditor;

import org.iocaste.documents.common.Documents;
import org.iocaste.packagetool.common.InstallData;
import org.iocaste.protocol.Message;
import org.iocaste.shell.common.AbstractPage;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.View;

public class Main extends AbstractPage {
    private Context context;
    
    public Main() {
        export("install", "install");
    }
    
    public final void acceptitens(View view) {
        context.tablehelper.refresh(view);
        context.tablehelper.accept();
    }
    
    public final void additens(View view) {
        context.tablehelper.refresh(view);
        context.tablehelper.add();
    }
    
    /**
     * 
     * @param vdata
     */
    public final void delete(View vdata) {
        Request.delete(vdata, this);
    }
    
    public final void display(View view) {
        DataForm form = view.getElement("model");
        String modelname = form.get("NAME").get();
        String message = Request.load(modelname, context);
        
        if (message != null) {
            view.message(Const.ERROR, message);
            return;
        }
        
        context.mode = Context.DISPLAY;
        view.redirect("itens");
    }
    
    /**
     * 
     * @param vdata
     */
    public final void form(View vdata) {
        Response.form(vdata, context);
    }
    
    @Override
    public final void init(View view) {
        Documents documents;
        
        if (!view.getPageName().equals("main"))
            return;
        
        documents = new Documents(this);
        context = new Context();
        context.modelmodel = documents.getModel("MODEL");
        context.function = this;
    }
    
    /**
     * 
     * @param vdata
     */
    public final void insert(View vdata) {
        Request.insert(vdata);
    }
    
    /**
     * 
     * @param vdata
     */
    public final void insertcancel(View vdata) {
        back(vdata);
    }
    
    /**
     * 
     * @param message
     * @return
     */
    public final InstallData install(Message message) {
        return Install.init();
    }
    
    /**
     * 
     * @param vdata
     */
    public final void itens(View view) {
        Response.itens(view, context);
    }
    
    /**
     * 
     * @param vdata
     */
    public final void main(View vdata) {
        Response.main(vdata, context);
    }

    public final void remove(View view) {
        context.tablehelper.refresh(view);
        context.tablehelper.remove();
    }
    
    /**
     * 
     * @param vdata
     */
    public final void save(View vdata) {
        Request.save(vdata, context);
    }
    
    /**
     * 
     * @param view
     */
    public final void update(View view) {
        DataForm form = view.getElement("model");
        String modelname = form.get("NAME").get();
        String message = Request.load(modelname, context);
        
        if (message != null) {
            view.message(Const.ERROR, message);
            return;
        }
        
        context.mode = Context.UPDATE;
        view.redirect("itens");
    }
}
