package org.iocaste.shell.common;

import java.lang.reflect.Method;
import java.util.Map;

import org.iocaste.protocol.AbstractHandler;
import org.iocaste.protocol.IocasteException;
import org.iocaste.protocol.Message;

public class ExecAction extends AbstractHandler {
    public AbstractContext context;
    public ViewState state;
    public Map<String, ViewCustomAction> customactions;
    
    public static final String getMessage(AbstractContext context, String tag) {
        String text;
        
        if (tag == null)
            return null;
        text = context.messages.get(tag);
        return (text == null)? tag : text;
    }
    
    @Override
    public Object run(Message message) throws Exception {
        Element element;
        ControlComponent control;
        ViewCustomAction customaction;
        Method method;
        View view = message.get("view");
        AbstractPage page = getFunction();
        
        state.view = view;
        state.rapp = state.rpage = null;
        state.initialize = false;
        context.view = view;
        context.function = page;
        context.action = context.control = message.getst("action");
        
        customaction = customactions.get(context.action);
        if (customaction == null) {
            element = context.view.getElement(context.action);
            if (element == null) {
                if (context.action.length() == 0)
                    return state;
                
                try {
                    method = page.getClass().getMethod(context.action);
                } catch (Exception e) {
                    throw new IocasteException(new StringBuilder(
                            "no defined element for \"").append(context.action).
                            append("\".").toString());
                }
                method.invoke(page);
                state.messagetext = getMessage(context, state.messagetext);
                return state;
            }
            
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
        
        state.messagetext = getMessage(context, state.messagetext);
        return state;
    }
}
