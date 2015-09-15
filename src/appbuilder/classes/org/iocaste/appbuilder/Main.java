package org.iocaste.appbuilder;

import org.iocaste.appbuilder.common.AbstractMessagesSource;
import org.iocaste.appbuilder.common.AppBuilderLink;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.PageBuilderDefaultInstall;
import org.iocaste.appbuilder.common.cmodelviewer.AbstractModelViewer;
import org.iocaste.appbuilder.common.cmodelviewer.DisplayConfig;
import org.iocaste.appbuilder.common.cmodelviewer.Load;
import org.iocaste.appbuilder.common.cmodelviewer.MaintenanceConfig;
import org.iocaste.appbuilder.common.cmodelviewer.MaintenanceInput;
import org.iocaste.appbuilder.common.cmodelviewer.MaintenanceSpec;
import org.iocaste.appbuilder.common.cmodelviewer.Save;
import org.iocaste.appbuilder.common.cmodelviewer.Validate;
import org.iocaste.documents.common.ComplexModel;
import org.iocaste.documents.common.Documents;

public class Main extends AbstractModelViewer {
    
    @Override
    public final void config(PageBuilderContext context) throws Exception {
        ComplexModel cmodel;
        Documents documents;
        String msgsource = getParameter("msgsource");
        AppBuilderLink link = getReceivedLink();
        
        add(new Messages());
        
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

        if (link.cmodel != null) {
            documents = new Documents(context.function);
            cmodel = documents.getComplexModel(link.cmodel);
            if (cmodel != null)
                link.appname = cmodel.getHeader().getPackage();
        }
        
        loadManagedModule(context, link);
    }
    
    @Override
    protected final void installConfig(PageBuilderDefaultInstall defaultinstall)
    {
        defaultinstall.setProfile("APPBUILDER");
        defaultinstall.setProgramAuthorization("APPBUILDER.EXECUTE");
        
        installObject("messages", new TextsInstall());
    }
}

class Messages extends AbstractMessagesSource {
    
    public Messages() {
        put("code.exists", "Documento já existe.");
        put("invalid.code", "Código de documento inválido.");
        put("record.saved", "Documento %s gravado com sucesso.");
    }
}