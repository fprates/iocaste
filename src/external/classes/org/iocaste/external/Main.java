package org.iocaste.external;

import org.iocaste.appbuilder.common.AppBuilderLink;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.PageBuilderDefaultInstall;
import org.iocaste.appbuilder.common.cmodelviewer.AbstractModelViewer;
import org.iocaste.appbuilder.common.cmodelviewer.EntityCustomPage;
import org.iocaste.appbuilder.common.cmodelviewer.Load;
import org.iocaste.appbuilder.common.cmodelviewer.Save;
import org.iocaste.appbuilder.common.cmodelviewer.SelectConfig;
import org.iocaste.appbuilder.common.cmodelviewer.Validate;
import org.iocaste.external.install.FunctionsInstall;
import org.iocaste.external.install.PortsInstall;
import org.iocaste.external.install.TextsInstall;

public class Main extends AbstractModelViewer {

    @Override
    public void config(PageBuilderContext context) throws Exception {
        AppBuilderLink link;
        
        link = getReceivedLink();
        switch (link.entity) {
        case "externalstruct":
            link.maintenancespec = new ExternalMaintenanceSpec();
            link.maintenanceconfig = new ExternalMaintenanceConfig();
            link.maintenanceinput = new ExternalMaintenanceInput();
            link.createselectconfig = new SelectConfig("XTRNL_STRUCTURE");
            link.displayconfig = new ExternalDisplayConfig();
            link.validate = new Validate();
            link.save = new Save();
            link.updateload = new Load(link.edit1view);
            link.displayload = new Load(link.display1view);
            link.custompage = new ExternalCustomPage();
            
            break;
        }
        
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
        link.appname = "iocaste-external";
        
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

class ExternalCustomPage extends EntityCustomPage {
    
    @Override
    public final void execute() {
        super.execute();
        put("importmodel", new ImportModel());
    }
}
