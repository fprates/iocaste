package org.iocaste.sh;

import org.iocaste.packagetool.common.InstallData;
import org.iocaste.protocol.Message;
import org.iocaste.shell.common.AbstractPage;
import org.iocaste.shell.common.PageContext;
import org.iocaste.shell.common.PageStackItem;
import org.iocaste.shell.common.Shell;
import org.iocaste.shell.common.View;

public class Main extends AbstractPage {
    private Context context;
    
    public Main() {
        export("install", "install");
    }
    
    /**
     * 
     * @param controldata
     * @param view
     */
    @Override
    public void back() {
        PageStackItem entry = new Shell(this).popPage(context.view);
        
        context.view.redirect(entry.getApp(), entry.getPage());
        context.view.dontPushPage();
        context.view.setReloadableView(false);
    }
    
    public final void choose() {
        updateView(Request.choose(context.view));
        back();
    }
    
    @Override
    public final PageContext init(View view) {
        context = new Context();
        
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
    
    public void main() {
        Response.main(context);
    }
    
    public void search() {
        Request.search(context);
    }
}
