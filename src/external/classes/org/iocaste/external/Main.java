package org.iocaste.external;

import java.util.Map;

import org.iocaste.appbuilder.common.AppBuilderLink;
import org.iocaste.appbuilder.common.FieldProperty;
import org.iocaste.appbuilder.common.GetFieldsProperties;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.PageBuilderDefaultInstall;
import org.iocaste.appbuilder.common.cmodelviewer.AbstractModelViewer;
import org.iocaste.appbuilder.common.cmodelviewer.DisplayMaintenancePage;
import org.iocaste.appbuilder.common.cmodelviewer.EntityPage;
import org.iocaste.appbuilder.common.cmodelviewer.Load;
import org.iocaste.appbuilder.common.cmodelviewer.MaintenancePage;
import org.iocaste.appbuilder.common.cmodelviewer.Save;
import org.iocaste.appbuilder.common.cmodelviewer.Validate;
import org.iocaste.documents.common.ComplexModel;
import org.iocaste.documents.common.Documents;
import org.iocaste.external.install.FunctionsInstall;
import org.iocaste.external.install.PortsInstall;

public class Main extends AbstractModelViewer {

    @Override
    public void config(GetFieldsProperties config) {
        Map<String, FieldProperty> properties;
        FieldProperty property;
        
        property = new FieldProperty();
        property.setsecretstate = true;
        property.secret = true;

        properties = config.instance("main");
        properties.put("SECRET", property);
    }
    
    @Override
    public void config(PageBuilderContext context) throws Exception {
        ComplexModel cmodel;
        Documents documents;
        AppBuilderLink link;
        
        context.messages = new Messages();
        link = getReceivedLink();
        if (link == null)
            return;
        switch (link.entity) {
        case "externalstruct":
            link.validate = new Validate();
            link.save = new Save();
            link.updateload = new Load(link.edit1view);
            link.displayload = new Load(link.display1view);
            link.custompage = new ExternalCustomPage();
            link.displaypage = new ExternalDisplayPage();
            link.entitypage = new ExternalEntityPage();
            break;
        default:
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
        }
        
        setExtendedContext(new ExternalContext(context));
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
        link.appname = "iocaste-external";
        
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
        link.appname = "iocaste-external";

        installObject("functions", new FunctionsInstall());
        installObject("ports", new PortsInstall());
    }

}
