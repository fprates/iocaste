package org.iocaste.appbuilder.common.editor;

import org.iocaste.appbuilder.common.AbstractActionHandler;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.docmanager.common.Manager;

public class ComplexDocumentEditor {

    public ComplexDocumentEditor(String name, PageBuilderContext context,
            Manager manager) {
        Validate validate;
        AbstractActionHandler save;
        MaintainanceConfig config;
        String create = name.concat("create");
        
        context.setViewSpec(create, new SelectSpec());
        context.setViewConfig(create, new SelectConfig(manager));
        validate = new Validate();
        validate.setManager(manager);
        validate.setRedirect(create.concat("1"));
        context.setActionHandler(create, "create", validate);
        
        create = name.concat("create1");
        context.setViewSpec(create, new MaintainanceSpec(manager));
        config = new MaintainanceConfig(manager);
        config.setTitle(name);
        context.setViewConfig(create, config);
        save = new Save();
        save.setManager(manager);
        context.setActionHandler(create, "save", save);
//        action = new Load(manager);
//        context.setActionHandler("partnercreate", "select", action);
    }
}
