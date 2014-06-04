package org.iocaste.appbuilder.common.editor;

import org.iocaste.appbuilder.common.AbstractActionHandler;
import org.iocaste.appbuilder.common.AbstractViewInput;
import org.iocaste.appbuilder.common.AbstractViewSpec;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.docmanager.common.Manager;

public class ComplexDocumentEditor {

    public ComplexDocumentEditor(String name, PageBuilderContext context,
            Manager manager) {
        MaintenanceConfig maintenanceconfig;
        AbstractViewSpec selspec, maintenancespec;
        AbstractViewInput maintenanceinput;
        AbstractActionHandler save;
        String create, create1, edit, edit1;
        ExtendedContext extcontext;

        create = name.concat("create");
        create1 = create.concat("1");
        edit = name.concat("edit");
        edit1 = edit.concat("1");
        context.addManager(create, manager);
        context.addManager(create1, manager);
        context.addManager(edit, manager);
        context.addManager(edit1, manager);
        
        extcontext = new ExtendedContext();
        extcontext.redirect = create1;
        
        selspec = new SelectSpec();
        maintenancespec = new MaintenanceSpec();
        maintenanceconfig = new MaintenanceConfig();
        maintenanceinput = new MaintenanceInput(extcontext);
        save = new Save();
        
        /*
         * create
         */
        context.setViewSpec(create, selspec);
        context.setViewConfig(create, new SelectConfig("create"));
        context.setActionHandler(create, "create", new Validate(extcontext));
        
        context.setViewSpec(create1, maintenancespec);
        context.setViewConfig(create1, maintenanceconfig);
        context.setViewInput(create1, maintenanceinput);
        context.setActionHandler(create1, "save", save);
        
        /*
         * edit
         */
        context.setViewSpec(edit, selspec);
        context.setViewConfig(edit, new SelectConfig("edit"));
        context.setActionHandler(edit, "edit", new Load(extcontext, edit1));
        
        context.setViewSpec(edit1, maintenancespec);
        context.setViewConfig(edit1, maintenanceconfig);
        context.setViewInput(edit1, maintenanceinput);
        context.setActionHandler(edit1, "save", save);
        
//        action = new Load(manager);
//        context.setActionHandler("partnercreate", "select", action);
    }
}
