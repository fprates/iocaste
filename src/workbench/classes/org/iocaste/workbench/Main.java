package org.iocaste.workbench;

import org.iocaste.appbuilder.common.AppBuilderLink;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.PageBuilderDefaultInstall;
import org.iocaste.appbuilder.common.cmodelviewer.AbstractModelViewer;
import org.iocaste.appbuilder.common.cmodelviewer.EntityCustomPage;
import org.iocaste.appbuilder.common.cmodelviewer.Load;
import org.iocaste.appbuilder.common.cmodelviewer.MaintenanceInput;
import org.iocaste.appbuilder.common.cmodelviewer.Validate;
import org.iocaste.workbench.install.ProjectInstall;
import org.iocaste.workbench.project.ModelAdd;
import org.iocaste.workbench.project.ModelPick;
import org.iocaste.workbench.project.ProjectDisplayConfig;
import org.iocaste.workbench.project.ProjectEditConfig;
import org.iocaste.workbench.project.ProjectSpec;

public class Main extends AbstractModelViewer {
    
    @Override
    public void config(PageBuilderContext context) {
        AppBuilderLink link;
        
        link = getReceivedLink();
        link.displayconfig = new ProjectDisplayConfig();
        link.maintenancespec = new ProjectSpec();
        link.maintenanceinput = new MaintenanceInput();
        link.maintenanceconfig = new ProjectEditConfig();
        link.updateload = new Load(link.edit1view);
        link.displayload = new Load(link.display1view);
        link.validate = new Validate();
        link.save = new ProjectSave();
        link.custompage = new WorkbenchCustomPage();
        link.inputvalidate = new WorkbenchValidate();
        setExtendedContext(new WorkbenchContext(context));
        
        loadManagedModule(context, link);
    }

    @Override
    protected void installConfig(PageBuilderDefaultInstall defaultinstall) {
        AppBuilderLink link;
        
        defaultinstall.setProfile("DEVELOP");
        defaultinstall.setProgramAuthorization("WB");
        
        link = defaultinstall.builderLinkInstance();
        link.change = "WBPROJECTCH";
        link.create = "WBPROJECTCR";
        link.display = "WBPROJECTDS";
        link.entity = "project";
        link.cmodel = "WB_PROJECT";
        link.taskgroup = "DEVELOP";
        link.appname = "iocaste-workbench";
        
        installObject("project", new ProjectInstall());
    }

}

class WorkbenchCustomPage extends EntityCustomPage {
    
    @Override
    public final void execute() {
        super.execute();
        put("model_add", new ModelAdd());
        put("model_pick", new ModelPick());
    }
}