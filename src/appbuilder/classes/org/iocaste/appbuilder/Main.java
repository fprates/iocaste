package org.iocaste.appbuilder;

import org.iocaste.appbuilder.common.AppBuilderLink;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.PageBuilderDefaultInstall;
import org.iocaste.appbuilder.common.cmodelviewer.AbstractModelViewer;
import org.iocaste.appbuilder.common.cmodelviewer.DisplayMaintenancePage;
import org.iocaste.appbuilder.common.cmodelviewer.EntityPage;
import org.iocaste.appbuilder.common.cmodelviewer.Load;
import org.iocaste.appbuilder.common.cmodelviewer.MaintenancePage;
import org.iocaste.appbuilder.common.cmodelviewer.Save;
import org.iocaste.appbuilder.common.cmodelviewer.Validate;
import org.iocaste.appbuilder.install.PortalInstall;
import org.iocaste.documents.common.ComplexModel;
import org.iocaste.documents.common.Documents;

public class Main extends AbstractModelViewer {
    
    @Override
    public final void config(PageBuilderContext context) throws Exception {
        ComplexModel cmodel;
        Documents documents;
        AppBuilderLink link = getReceivedLink();
        
        context.messages = new Messages();
        link.updateload = new Load(link.edit1view);
        link.displayload = new Load(link.display1view);
        link.validate = new Validate();
        link.save = new Save();
        link.custompage = new MaintenancePage();
        link.displaypage = new DisplayMaintenancePage();
        link.entitypage = new EntityPage();
        
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
        
        installObject("portal", new PortalInstall());
    }
}
