package org.iocaste.external;

import org.iocaste.appbuilder.common.AppBuilderLink;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.PageBuilderDefaultInstall;
import org.iocaste.appbuilder.common.cmodelviewer.AbstractModelViewer;
import org.iocaste.external.install.TextsInstall;

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
        link.create = "XTRNLPORTCR";
        link.change = "XTRNLPORTCH";
        link.display = "XTRNLPORTDS";
        link.cmodel = "XTRNL_CONNECTION";
        link.taskgroup = "EXTERNAL";
        link.entity = "externalconn";
        
        link = defaultinstall.builderLinkInstance();
        link.create = "XTRNLSTRCR";
        link.change = "XTRNLSTRCH";
        link.display = "XTRNLSTRDS";
        link.cmodel = "XTRNL_STRUCTURE";
        link.taskgroup = "EXTERNAL";
        link.entity = "externalstruct";
        
        link = defaultinstall.builderLinkInstance();
        link.create = "XTRNLFNCCR";
        link.change = "XTRNLFNCCH";
        link.display = "XTRNLFNCDS";
        link.cmodel = "XTRNL_FUNCTION";
        link.taskgroup = "EXTERNAL";
        link.entity = "externalfunction";

        installObject("texts", new TextsInstall());
        installObject("functions", new FunctionsInstall());
        installObject("models", new PortsInstall());
    }

}
