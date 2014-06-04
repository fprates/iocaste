package org.iocaste.appbuilder.common.editor;

import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.docmanager.common.Manager;

public class ComplexDocumentEditor {

    public ComplexDocumentEditor(String name, PageBuilderContext context,
            Manager manager) {
        Validate validate;
        MaintainanceConfig config;
        String create, create1;
        ExtendedContext extcontext = new ExtendedContext();

        create = name.concat("create");
        create1 = create.concat("1");
        
        context.addManager(create, manager);
        context.addManager(create1, manager);
        validate = new Validate(extcontext);
        validate.setRedirect(create1);
        context.setViewSpec(create, new SelectSpec());
        context.setViewConfig(create, new SelectConfig());
        context.setActionHandler(create, "create", validate);
        
        config = new MaintainanceConfig();
        config.setTitle(name);
        context.setViewSpec(create1, new MaintainanceSpec());
        context.setViewConfig(create1, config);
        context.setViewInput(create1, new MaintainanceInput(extcontext));
        context.setActionHandler(create1, "save", new Save());
        
        
//        action = new Load(manager);
//        context.setActionHandler("partnercreate", "select", action);
    }
}
