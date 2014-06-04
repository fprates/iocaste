package org.iocaste.appbuilder.common.editor;

import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.docmanager.common.Manager;

public class ComplexDocumentEditor {

    public ComplexDocumentEditor(String name, PageBuilderContext context,
            Manager manager) {
        Validate validate;
        String create = name.concat("create");
        
        context.setViewSpec(create, new SelectSpec());
        context.setViewConfig(create, new SelectConfig(manager));
        
        validate = new Validate();
        validate.setManager(manager);
        validate.setRedirect(create.concat("1"));
        context.setActionHandler(create, "create", validate);
        
//        action = new Load(manager);
//        context.setActionHandler("partnercreate", "select", action);
    }
}
