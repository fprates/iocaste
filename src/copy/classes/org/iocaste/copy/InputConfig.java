package org.iocaste.copy;

import org.iocaste.appbuilder.common.AbstractViewConfig;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.shell.common.DataForm;
import org.iocaste.shell.common.InputComponent;

public class InputConfig extends AbstractViewConfig {

    @Override
    protected void execute(PageBuilderContext context) {
        DataForm form;
        InputComponent input;
        
        form = getElement("model");
        form.importModel("MODEL", context.function);
        form.show("NAME", "NAMESPACE");
        input = form.get("NAME");
        input.setObligatory(true);
        context.view.setFocus(input);
        
        form = getElement("port");
        form.importModel("XTRNL_PORT_HEAD", context.function);
        form.show("PORT_NAME");
        input = form.get("PORT_NAME");
        input.setObligatory(true);
    }

}
