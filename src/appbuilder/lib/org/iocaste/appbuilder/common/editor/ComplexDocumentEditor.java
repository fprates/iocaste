package org.iocaste.appbuilder.common.editor;

import org.iocaste.appbuilder.common.AbstractActionHandler;
import org.iocaste.appbuilder.common.AbstractViewInput;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.docmanager.common.Manager;

public class ComplexDocumentEditor {

    public ComplexDocumentEditor(String name, PageBuilderContext context,
            Manager manager) {
        Validate validate;
        AbstractActionHandler save;
        AbstractViewInput input;
        MaintainanceConfig config;
        String create = name.concat("create");
        ExtendedContext extcontext = new ExtendedContext();

        validate = new Validate(extcontext);
        validate.setManager(manager);
        validate.setRedirect(create.concat("1"));
        context.setViewSpec(create, new SelectSpec());
        context.setViewConfig(create, new SelectConfig(manager));
        context.setActionHandler(create, "create", validate);
        
        create = name.concat("create1");
        config = new MaintainanceConfig(manager);
        config.setTitle(name);
        input = new MaintainanceInput(extcontext);
        input.setManager(manager);
        context.setViewSpec(create, new MaintainanceSpec(manager));
        context.setViewConfig(create, config);
        context.setViewInput(create, input);
        save = new Save();
        save.setManager(manager);
        context.setActionHandler(create, "save", save);
//        action = new Load(manager);
//        context.setActionHandler("partnercreate", "select", action);
    }
}
