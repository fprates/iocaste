package org.iocaste.external;

import org.iocaste.appbuilder.common.AppBuilderLink;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.PageBuilderDefaultInstall;
import org.iocaste.appbuilder.common.cmodelviewer.AbstractModelViewer;

public class Main extends AbstractModelViewer {

    @Override
    public void config(PageBuilderContext context) throws Exception {
        AppBuilderLink link;
        
        link = getReceivedLink();
        setExtendedContext(new ExternalContext());
        loadManagedModule(context, link);
    }

    @Override
    protected void installConfig(PageBuilderDefaultInstall defaultinstall) {
        AppBuilderLink link;
        
        defaultinstall.setProfile("ADMIN");
        defaultinstall.setProgramAuthorization("XTRNL");
        
        link = defaultinstall.builderLinkInstance();
        link.create = "XTRNLCR";
        link.change = "XTRNLCH";
        link.display = "XTRNLDS";
        link.cmodel = "XTRNL_CONNECTION";
        link.taskgroup = "ADMIN";
        link.entity = "externalconn";
        
        installObject("models", new ModelsInstall());
    }

}
