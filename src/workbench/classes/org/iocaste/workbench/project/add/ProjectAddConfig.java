package org.iocaste.workbench.project.add;

import org.iocaste.appbuilder.common.AbstractViewConfig;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.dataformtool.DataFormToolData;
import org.iocaste.workbench.AbstractCommand;
import org.iocaste.workbench.ActionContext;
import org.iocaste.workbench.CommandArgument;
import org.iocaste.workbench.Context;

public class ProjectAddConfig extends AbstractViewConfig {
    
    @Override
    protected void execute(PageBuilderContext context) {
        ActionContext actionctx;
        CommandArgument argument;
        DataFormToolData dataform = getTool("inputform");
        Context extcontext = getExtendedContext();
        
        actionctx = extcontext.actions.get("project-add");
        dataform.model = "WB_PROJECT_HEAD";
        for (String name : actionctx.arguments.keySet()) {
            argument = actionctx.arguments.get(name);
            dataform.itemInstance(argument.field).required =
                    (argument.type == AbstractCommand.REQUIRED);
        }
    }
}

