package org.iocaste.workbench;

import org.iocaste.appbuilder.common.AbstractViewConfig;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.shell.common.InputComponent;

public class MainConfig extends AbstractViewConfig {

    @Override
    protected void execute(PageBuilderContext context) {
        InputComponent command = getElement("command");
        
        getNavControl().setTitle("iocaste.workbench");
        context.view.setFocus(command);
        command.setObligatory(true);
        command.setLength(256);
        command.setVisibleLength(64);
    }

}
