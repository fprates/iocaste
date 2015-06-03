package org.iocaste.appbuilder;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.appbuilder.common.AppBuilderLink;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.PageBuilderDefaultInstall;
import org.iocaste.appbuilder.common.ViewSpecItem;
import org.iocaste.appbuilder.common.cmodelviewer.AbstractModelViewer;
import org.iocaste.appbuilder.common.cmodelviewer.DisplayConfig;
import org.iocaste.appbuilder.common.cmodelviewer.Load;
import org.iocaste.appbuilder.common.cmodelviewer.MaintenanceConfig;
import org.iocaste.appbuilder.common.cmodelviewer.MaintenanceInput;
import org.iocaste.appbuilder.common.cmodelviewer.MaintenanceSpec;
import org.iocaste.appbuilder.common.cmodelviewer.Save;
import org.iocaste.appbuilder.common.cmodelviewer.Validate;

public class Main extends AbstractModelViewer {
    
    @Override
    public final void config(PageBuilderContext context) throws Exception {
        String module = getParameter("module");
        String msgsource = getParameter("msgsource");
        AppBuilderLink link = getReceivedLink();
        Map<Object, Object> messages = new HashMap<>();
        
        messages.put("code.exists", "Documento já existe.");
        messages.put("invalid.code", "Código de documento inválido.");
        messages.put("record.saved", "Documento %s gravado com sucesso.");
        addMessages(messages);
        
        if (msgsource != null)
            setMessageSource(msgsource);
        
        link.displayconfig = new DisplayConfig();
        link.maintenancespec = new MaintenanceSpec();
        link.maintenanceinput = new MaintenanceInput();
        link.maintenanceconfig = new MaintenanceConfig();
        link.updateload = new Load(link.edit1view);
        link.displayload = new Load(link.display1view);
        link.validate = new Validate();
        link.save = new Save();
        
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