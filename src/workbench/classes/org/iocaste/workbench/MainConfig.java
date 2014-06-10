package org.iocaste.workbench;

import org.iocaste.appbuilder.common.AbstractViewConfig;
import org.iocaste.appbuilder.common.NavControl;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.InputComponent;

public class MainConfig extends AbstractViewConfig {

    @Override
    protected void execute(PageBuilderContext context) {
        DataForm head;
        NavControl control;
        InputComponent input;
        
        control = getNavControl();
        control.add("create");
        
        head = getElement("head");
        head.importModel("WB_PROJECT", context.function);
        input = head.get("PROJECT_ID");
        input.setObligatory(true);
        
        context.view.setFocus(input);
    }

}
