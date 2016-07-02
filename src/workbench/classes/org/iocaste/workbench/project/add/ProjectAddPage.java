package org.iocaste.workbench.project.add;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.appbuilder.common.AbstractActionHandler;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.ViewContext;
import org.iocaste.appbuilder.common.panel.AbstractPanelPage;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.workbench.AbstractCommand;
import org.iocaste.workbench.ActionContext;
import org.iocaste.workbench.CommandArgument;
import org.iocaste.workbench.Context;

public class ProjectAddPage extends AbstractPanelPage {

    @Override
    public void execute() {
        set(new ProjectAddSpec());
        set(new ProjectAddConfig());
        set(new ProjectAddInput());
        action("project-add", new ParameterTransport("project-add"));
    }
}

class ParameterTransport extends AbstractActionHandler {
    private String action;
    
    public ParameterTransport(String action) {
        this.action = action;
    }

    @Override
    protected void execute(PageBuilderContext context) throws Exception {
        AbstractActionHandler handler;
        AbstractCommand command;
        CommandArgument argument;
        ViewContext mainctx;
        Context extcontext = getExtendedContext();
        ActionContext actionctx = extcontext.actions.get(action);
        Map<String, CommandArgument> arguments = actionctx.arguments;
        Map<String, String> parameters = new HashMap<>();
        ExtendedObject object = getdf("inputform");
        
        for (String key : arguments.keySet()) {
            argument = arguments.get(key);
            parameters.put(key, object.get(argument.field));
        }
        
        mainctx = context.getView("main");
        command = mainctx.getActionHandler(action);
        command.set(parameters);
        command.call(context);
        if (!actionctx.mainrestart)
            return;
        handler = mainctx.getActionHandler("load");
        handler.run(context);
        init("main", extcontext);
        back();
    }
}