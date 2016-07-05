package org.iocaste.workbench;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.iocaste.appbuilder.common.AbstractActionHandler;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.documents.common.ComplexDocument;
import org.iocaste.documents.common.ExtendedObject;

public abstract class AbstractCommand extends AbstractActionHandler {
    public static final byte REQUIRED = 0;
    public static final byte OPTIONAL = 1;
    private Map<String, Object> parameters;
    protected Map<String, CommandArgument> arguments;
    protected Map<String, Set<Object>> values;
    protected boolean checkproject, checkmodel, checkview, checkparameters;
    private ActionContext actionctx;
    
    public AbstractCommand(String action, Context extcontext) {
        actionctx = new ActionContext();
        extcontext.actions.put(action, actionctx);
        actionctx.name = action;
        actionctx.handler = this;
        arguments = actionctx.arguments = new HashMap<>();
        extcontext.commands.put(action, this);
        values = new HashMap<>();
        checkproject = checkparameters = true;
    }
    
    public final String areParametersValid(Map<String, Object> parameters) {
        for (String key : arguments.keySet())
            if ((arguments.get(key).type == REQUIRED) &&
                    !parameters.containsKey(key))
                return "parameter.required";
        
        if (checkparameters && (arguments.size() > 0))
            for (String key : parameters.keySet()) {
                if (!arguments.containsKey(key))
                    return "invalid.parameter";
                if (!values.containsKey(key))
                    continue;
                if (!values.get(key).contains(parameters.get(key)))
                    return "invalid.value";
            }
        return null;
    }
    
    protected final void autoset(ComplexDocument document) {
        CommandArgument argument;
        for (String name : parameters.keySet()) {
            argument = arguments.get(name);
            document.set(argument.field, parameters.get(name));
        }
    }
    
    protected final void autoset(ExtendedObject object) {
        CommandArgument argument;
        for (String name : parameters.keySet()) {
            argument = arguments.get(name);
            if (argument.bool)
                object.set(argument.field, getBooleanParameter(name));
            else
                object.set(argument.field, parameters.get(name));
        }
    }
    
    public final Object call(PageBuilderContext context) throws Exception {
        Context extcontext = context.getView().getExtendedContext();
        run(context);
        return extcontext.callreturn;
    }
    
    protected final boolean contains(String name) {
        return parameters.containsKey(name);
    }
    
    protected abstract Object entry(PageBuilderContext context)
            throws Exception;
    
    @Override
    protected final void execute(PageBuilderContext context) throws Exception {
        Context extcontext = context.getView().getExtendedContext();
        extcontext.callreturn = entry(context);
    }
    
    protected final Object get(String name) {
        return parameters.get(name);
    }
    
    protected final ActionContext getActionContext() {
        return actionctx;
    }
    
    protected final boolean getBooleanParameter(String name) {
        Object value = parameters.get(name);
        if (value == null)
            return false;
        if (value instanceof String)
            return Boolean.parseBoolean((String)value);
        return (boolean)value;
    }
    
    protected final int geti(String name) {
        Object value = parameters.get(name);
        return (value instanceof BigDecimal)?
                ((BigDecimal)value).intValue() : (int)value;
    }
    
    protected final Set<String> getKeys() {
        return parameters.keySet();
    }
    
    protected final String getst(String name) {
        return (String)parameters.get(name);
    }
    
    public final String isValidContext(Context extcontext) {
        if (checkproject && (extcontext.project == null))
            return "undefined.project";
        if (checkmodel && (extcontext.model == null))
            return "undefined.model";
        if (checkview && (extcontext.view == null))
            return "undefined.view";
        return null;
    }
    
    protected final void optional(String name, String field, Object... options)
    {
        Set<Object> test;
        
        arguments.put(name, new CommandArgument(OPTIONAL, field));
        if ((options == null) || (options.length == 0))
            return;
        
        test = new HashSet<>();
        values.put(name, test);
        for (Object option : options)
            test.add(option);
    }
    
    protected final void optionalbl(String name, String field) {
        optional(name, field, true, false);
        arguments.get(name).bool = true;
    }
    
    protected final void required(String name, String field) {
        arguments.put(name, new CommandArgument(REQUIRED, field));
    }
    
    public final void set(Map<String, Object> parameters) {
        this.parameters = parameters;
    }
}
