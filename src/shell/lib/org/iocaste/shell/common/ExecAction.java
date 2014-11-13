package org.iocaste.shell.common;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import org.iocaste.protocol.AbstractHandler;
import org.iocaste.protocol.Message;

public class ExecAction extends AbstractHandler {
    public AbstractContext context;
    public ViewState state;
    public Map<String, ViewCustomAction> customactions;
    public Map<String, List<String>> validables;
    public Map<String, Validator> validators;

    @Override
    public Object run(Message message) throws Exception {
        Validator validator;
        InputComponent input;
        List<String> handlers;
        ViewCustomAction customaction;
        Method method;
        String action, error;
        String controlname = message.getString("action");
        View view = message.get("view");
        ControlComponent control = view.getElement(controlname);
        AbstractPage page = getFunction();
        
        if (context != null) {
            context.view = view;
            context.function = page;
        }
        
        state.view = view;
        state.rapp = state.rpage = null;
        action = (control == null)? controlname : control.getName();
        view.setActionControl(action);
        for (String name : validables.keySet()) {
            input = (InputComponent)view.getElement(name);
            if (input == null)
                continue;
            
            handlers = validables.get(name);
            for (String validatorname : handlers) {
                validator = validators.get(validatorname);
                validator.clear();
                validator.setContext(context);
                validator.setInput(input);
                validator.validate();
                error = validator.getMessage();
                if (error == null)
                    continue;
                context.view.setFocus(input);
                context.view.message(Const.ERROR, error);
                return state;
            }
        }
        
        action = (control == null)? controlname : control.getAction();
        customaction = customactions.get(action);
        if (customaction != null) {
            customaction.execute(context);
        } else {
            method = page.getClass().getMethod(action);
            method.invoke(page);
        }
        
        return state;
    }

}
