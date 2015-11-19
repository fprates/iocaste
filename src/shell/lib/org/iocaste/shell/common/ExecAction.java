package org.iocaste.shell.common;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

import org.iocaste.protocol.AbstractHandler;
import org.iocaste.protocol.Message;

public class ExecAction extends AbstractHandler {
    private MessageSource messages;
    public AbstractContext context;
    public ViewState state;
    public Map<String, ViewCustomAction> customactions;
    public Map<String, Set<String>> validables;
    public Map<String, Validator> validators;
    
    private final String getMessage(String message) {
        String text;
        
        if (message == null)
            return null;
        text = messages.get(message);
        return (text == null)? message : text;
    }
    
    @Override
    public Object run(Message message) throws Exception {
        Element element;
        ControlComponent control;
        Validator validator;
        InputComponent input;
        Set<String> handlers;
        ViewCustomAction customaction;
        Method method;
        String error;
        View view = message.get("view");
        AbstractPage page = getFunction();
        
        if (context != null) {
            context.view = view;
            context.function = page;
        }
        
        state.view = view;
        state.rapp = state.rpage = null;
        state.initialize = false;
        context.action = context.control = message.getString("action");
        for (String name : validables.keySet()) {
            input = (InputComponent)view.getElement(name);
            if ((input == null) || !input.isEnabled())
                continue;
            
            handlers = validables.get(name);
            for (String validatorname : handlers) {
                validator = validators.get(validatorname);
                validator.clear();
                validator.setInput(input);
                validator.validate(context);
                error = validator.getMessage();
                if (error == null)
                    continue;
                context.view.setFocus(input);
                context.function.message(Const.ERROR, getMessage(error));
                return state;
            }
        }
        
        customaction = customactions.get(context.action);
        if (customaction == null) {
            element = context.view.getElement(context.action);
            if (element.isControlComponent()) {
                control = (ControlComponent)element;
                context.action = control.getAction();
                customaction = customactions.get(context.action);
            }
        }
        
        if (customaction != null) {
            customaction.execute(context);
        } else {
            method = page.getClass().getMethod(context.action);
            method.invoke(page);
        }
        
        state.messagetext = getMessage(state.messagetext);
        return state;
    }
    
    public final void setMessages(MessageSource messages) {
        this.messages = messages;
    }

}
