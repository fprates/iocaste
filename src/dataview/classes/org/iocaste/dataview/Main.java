package org.iocaste.dataview;

import org.iocaste.documents.common.Documents;
import org.iocaste.packagetool.common.InstallData;
import org.iocaste.protocol.Message;
import org.iocaste.shell.common.AbstractPage;
import org.iocaste.shell.common.PageContext;
import org.iocaste.shell.common.View;

public class Main extends AbstractPage {
    private Context context;
    
    public Main() {
        export("install", "install");
    }
    
    /*
     * (não-Javadoc)
     * @see org.iocaste.shell.common.AbstractPage#init(
     *     org.iocaste.shell.common.View)
     */
    @Override
    public final PageContext init(View view) {
        context = new Context();
        context.modelmodel = new Documents(this).getModel("MODEL");
        
        return context;
    }
    
    /**
     * 
     * @param message
     * @return
     */
    public final InstallData install(Message message) {
        return Install.init();
    }
    
    public final void list() {
        Response.list(context);
    }
    
    public final void main() {
        Response.main(context);
    }
    
    public final void select() {
        Request.select(context);
    }
}
