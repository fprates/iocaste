package org.iocaste.appbuilder.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Map;

import org.iocaste.packagetool.common.InstallData;
import org.iocaste.protocol.Message;
import org.iocaste.shell.common.AbstractContext;
import org.iocaste.shell.common.AbstractPage;
import org.iocaste.shell.common.View;

public abstract class AbstractPageBuilder extends AbstractPage {
    private PageBuilderContext context;
    private StandardInstallContext installcontext;
    private PageBuilderDefaultInstall defaultinstall;
    private BuilderCustomAction customaction;
    
    public AbstractPageBuilder() {
        export("install", "install");
        export("fields_properties_get", new GetFieldsProperties());
    }

    
    protected final void add(AbstractMessagesSource messages) {
        addMessages(messages.get());
    }
    
    public abstract void config(PageBuilderContext context) throws Exception;
    
    public void config(GetFieldsProperties config) { };
    
    protected byte[] getApplicationContext(String installctx) throws Exception {
        byte[] buffer;
        int size;
        InputStream is;
        File file = new File(installctx);
        
        size = ((Number)file.length()).intValue();
        buffer = new byte[size];
        is = new FileInputStream(file);
        is.read(buffer);
        is.close();
        
        return buffer;
    }
    
    /*
     * (não-Javadoc)
     * @see org.iocaste.shell.common.AbstractPage#init(
     *    org.iocaste.shell.common.View)
     */
    @Override
    public AbstractContext init(View view) throws Exception {
        
        context = new PageBuilderContext();
        context.view = view;
        context.function = this;
        config(context);
        
        customaction = new BuilderCustomAction();
        reassignCustomActions(context);
        
        return context;
    }
    
    public final InstallData install(Message message) throws Exception {
        Map<String, AbstractInstallObject> objects;
        InstallData data;
        String pkgname = message.getString("name");
        
        installcontext = new StandardInstallContext();
        defaultinstall = new PageBuilderDefaultInstall(pkgname);
        installObject("default", defaultinstall);
        installConfig(defaultinstall);
        objects = installcontext.getObjects();
        objects.put("default", defaultinstall);
        data = installcontext.getInstallData();
        for (String name : objects.keySet())
            objects.get(name).run(installcontext);
        
        return data;
    }
    
    protected abstract void installConfig(
            PageBuilderDefaultInstall defaultinstall) throws Exception;
    
    protected final void installObject(
            String name, AbstractInstallObject object) {
        installcontext.put(name, object);
    }
    
    protected final void reassignCustomActions(PageBuilderContext context) {
        ViewContext viewctx;
        BuilderCustomView customview;
        AbstractViewSpec viewspec;
        ViewConfig viewconfig;
        AbstractViewInput viewinput;
        AbstractActionHandler handler;
        
        for (String name : context.getViews()) {
            viewctx = context.getView(name);
            viewspec = viewctx.getSpec();
            viewconfig = viewctx.getConfig();
            viewinput = viewctx.getInput();
            
            customview = new BuilderCustomView();
            customview.setViewSpec(viewspec);
            customview.setViewConfig(viewconfig);
            customview.setViewInput(viewinput);
            customview.setView(name);
            
            register(name, customview);
            for (String action : viewctx.getActions()) {
                handler = viewctx.getActionHandler(action);
                customaction.addHandler(name, action, handler);
                register(action, customaction);
            }
        }
    }
}
