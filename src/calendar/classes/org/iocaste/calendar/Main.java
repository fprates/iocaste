package org.iocaste.calendar;

import org.iocaste.appbuilder.common.AbstractPageBuilder;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.PageBuilderDefaultInstall;
import org.iocaste.shell.common.PageStackItem;
import org.iocaste.shell.common.Shell;

public class Main extends AbstractPageBuilder {
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
        String position;
        PageStackItem entry;
        
        entry = new Shell(this).popPage(context.view);
        position = new StringBuilder(entry.getApp()).append(".").
                append(entry.getPage()).toString();
        backTo(position);
        setReloadableView(false);
    }
    
//    public final void choose() {
//        updateView(Request.choose(context));
//        back();
//    }

    @Override
    public void config(PageBuilderContext context) throws Exception {
        // TODO Stub de método gerado automaticamente
        
    }

    @Override
    protected void installConfig(PageBuilderDefaultInstall defaultinstall)
            throws Exception {
        // TODO Stub de método gerado automaticamente
        
    }
}
