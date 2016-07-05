package org.iocaste.workbench.project;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.appbuilder.common.AbstractActionHandler;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.ViewContext;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.shell.common.Const;
import org.iocaste.workbench.AbstractCommand;
import org.iocaste.workbench.ActionContext;
import org.iocaste.workbench.CommandArgument;
import org.iocaste.workbench.Context;

public class ParameterTransport extends AbstractActionHandler {
    private String action, form;
    
    public ParameterTransport(String action, String form) {
        this.action = action;
        this.form = form;
    }

    @Override
    protected void execute(PageBuilderContext context) throws Exception {
        Object value;
        String message;
        AbstractActionHandler handler;
        AbstractCommand command;
        CommandArgument argument;
        ViewContext mainctx;
        Context extcontext = getExtendedContext();
        ActionContext actionctx = extcontext.actions.get(action);
        Map<String, Object> parameters = new HashMap<>();
        ExtendedObject object = getdf(form);
        
        if (actionctx.updateviewer != null)
            actionctx.updateviewer.preexecute(actionctx, object);
        
        for (String key : actionctx.arguments.keySet()) {
            argument = actionctx.arguments.get(key);
            value = object.get(argument.field);
            if (value != null)
                parameters.put(key, value);
        }
        
        mainctx = context.getView("main");
        command = mainctx.getActionHandler(action);
        message = command.areParametersValid(parameters);
        if (message != null) {
            message(Const.ERROR, message);
            return;
        }

        message = command.isValidContext(extcontext);
        if (message != null) {
            message(Const.ERROR, message);
            return;
        }
        
        command.set(parameters);
        value = command.call(context);
        if (actionctx.mainrestart) {
            handler = mainctx.getActionHandler("load");
            handler.run(context);
            init("main", extcontext);
        }
        
        if (actionctx.back)
            back();
        
        if (actionctx.updateviewer != null)
            actionctx.updateviewer.postexecute(value);
    }
}