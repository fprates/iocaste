package org.iocaste.shell.common;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

import org.iocaste.protocol.AbstractHandler;
import org.iocaste.protocol.Message;

public class ExecAction extends AbstractHandler {
    private MessageSource messages;
    public AbstractContext context;
    public ViewState state;
    public Map<String, ViewCustomAction> customactions;
    public Map<String, List<String>> validables;
    public Map<String, Validator> validators;
    
    private final String getMessage(String message) {
        String text = messages.get(message);
        return (text == null)? message : text;
    }
    
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
        state.initialize = false;
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
                context.function.message(Const.ERROR, getMessage(error));
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
        
        state.messagetext = getMessage(state.messagetext);
        return state;
    }
    
    public final void setMessages(MessageSource messages) {
        this.messages = messages;
    }

}
