package org.iocaste.dataview;

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
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.AbstractPage#init(
     *     org.iocaste.shell.common.View)
     */
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
    public final void list(View view) {
        Response.list(view, context);
    }
    
    /**
     * 
     * @param view
     */
    public final void main(View view) {
        Response.main(view, context);
    }
    
    /**
     * 
     * @param view
     */
    public final void select(View view) {
        Request.select(view, context);
    }
}
