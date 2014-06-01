package org.iocaste.appbuilder.common;

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
    
    public AbstractPageBuilder() {
        export("install", "install");
    }
    
    public abstract PageBuilderContext config();
    
    @SuppressWarnings("unchecked")
    protected final <T extends AbstractInstallObject> T getDefaultInstallObject(
            ) {
        return (T)defaultinstall;
    }
    
    /*
     * (não-Javadoc)
     * @see org.iocaste.shell.common.AbstractPage#init(
     *    org.iocaste.shell.common.View)
     */
    @Override
    public AbstractContext init(View view) {
        context = config();
        context.view = view;
        context.function = this;
        new PageBuilderEngine(context);
        return context;
    }
    
    public final InstallData install(Message message) {
        AbstractInstallObject object;
        Map<String, AbstractInstallObject> objects;
        InstallData data;
        String pkgname = message.getString("name");
        String pkgnameuc = pkgname.toUpperCase();
        
        installcontext = new StandardInstallContext();
        defaultinstall = new PageBuilderDefaultInstall(pkgname);
        defaultinstall.setLink(pkgnameuc, pkgname);
        defaultinstall.setProfile(pkgnameuc);
        defaultinstall.setTaskGroup(pkgnameuc);
        installConfig();
        objects = installcontext.getObjects();
        objects.put("default", defaultinstall);
        data = installcontext.getInstallData();
        for (String name : objects.keySet()) {
            object = objects.get(name);
            object.setInstallData(data);
            object.execute();
        }
        
        return data;
    }
    
    protected void installConfig() {
        installObject("default", defaultinstall);
    }
    
    protected final void installObject(
            String name, AbstractInstallObject object) {
        installcontext.put(name, object);
    }
}
