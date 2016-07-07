package org.iocaste.workbench.project.viewer;

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

public abstract class AbstractParameterTransport extends AbstractActionHandler {
    protected String action, tool;
    
    public AbstractParameterTransport(String action, String tool) {
        this.action = action;
        this.tool = tool;
    }

    protected ViewerUpdate execute(
            PageBuilderContext context, ExtendedObject object) throws Exception {
        Object value;
        String message;
        AbstractActionHandler handler;
        AbstractCommand command;
        CommandArgument argument;
        ViewContext mainctx;
        Context extcontext = getExtendedContext();
        ActionContext actionctx = extcontext.actions.get(action);
        Map<String, Object> parameters = new HashMap<>();
        
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
            return null;
        }

        message = command.isValidContext(extcontext);
        if (message != null) {
            message(Const.ERROR, message);
            return null;
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
        
        return actionctx.updateviewer;
    }

}
