package org.iocaste.appbuilder;

import org.iocaste.appbuilder.common.AppBuilderLink;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.PageBuilderDefaultInstall;
import org.iocaste.appbuilder.common.ViewSpecItem;
import org.iocaste.appbuilder.common.cmodelviewer.AbstractModelViewer;
import org.iocaste.appbuilder.common.cmodelviewer.Context;
import org.iocaste.appbuilder.common.cmodelviewer.MaintenanceConfig;
import org.iocaste.appbuilder.common.cmodelviewer.Validate;

public class Main extends AbstractModelViewer {
    
    @Override
    public final void config(PageBuilderContext context) throws Exception {
        String module = getParameter("module");
        String msgsource = getParameter("msgsource");
        AppBuilderLink link = getReceivedLink();
        
        if (msgsource != null)
            setMessageSource(msgsource);
        
        link.maintenanceconfig = new MaintenanceConfig();
        link.validate = new Validate();
        link.extcontext = new Context();
        if (module == null)
            loadManagedModule(context, link);
        else
            loadRemoteModule(context, module);
    }
    
    @Override
    protected final void installConfig(PageBuilderDefaultInstall defaultinstall)
    {
        defaultinstall.setProfile("APPBUILDER");
        defaultinstall.setProgramAuthorization("APPBUILDER.EXECUTE");
        
        installObject("messages", new TextsInstall());
    }
    
    private void loadRemoteModule(PageBuilderContext context, String module)
            throws Exception {
        String view;
        AutomatedViewSpec spec;
        int index;
        ViewSpecItem.TYPES[] types;
        String[] args;
        byte[] buffer = getApplicationContext(module);
        String[] lines = new String(buffer).split("\n");

        types = ViewSpecItem.TYPES.values();
        spec = null;
        for (String line : lines) {
            args = line.split(":");
            index = Integer.parseInt(args[1]);
            if ((index == -1) || (index >= 200))
                continue;
            
            switch (types[index]) {
            case VIEW:
                args = args[0].split("\\.");
                view = args[args.length - 1];
                spec = new AutomatedViewSpec();
                context.instance(view).set(spec);
                break;
            default:
                spec.add(args);
                break;
            }
        }
    }
}