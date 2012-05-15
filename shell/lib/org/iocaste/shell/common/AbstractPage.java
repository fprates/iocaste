package org.iocaste.shell.common;

import java.lang.reflect.Method;
import java.util.Locale;
import java.util.Map;

import org.iocaste.protocol.AbstractFunction;
import org.iocaste.protocol.Iocaste;
import org.iocaste.protocol.IocasteException;
import org.iocaste.protocol.Message;

public abstract class AbstractPage extends AbstractFunction {
    
    public AbstractPage() {
        export("get_view_data", "getViewData");
        export("exec_action", "execAction");
        export("custom_validation", "customValidation");
    }
    
    /**
     * 
     * @param controldata
     * @param view
     * @throws Exception 
     */
    public void back(ViewData view) throws Exception {
        String[] entry = new Shell(this).popPage(view);
        view.redirect(entry[0], entry[1]);
        view.dontPushPage();
    }
    
    /**
     * 
     * @param message
     * @return
     */
    public final String customValidation(Message message) throws Exception {
        ValidatorConfig config = message.get("config");
        Validator validator = (Validator)Class.forName(config.getClassName()).
                newInstance();
        
        validator.setFunction(this);
        
        return validator.validate(config);
    }
    
    /**
     * 
     * @param message
     * @return
     * @throws Exception
     */
    public final ViewData execAction(Message message) throws Exception {
        Method method;
        ViewData view = message.get("view");
        String action, controlname = message.getString("action");
        ControlComponent control = view.getElement(controlname);
        
        if (control != null && control.getType() == Const.SEARCH_HELP) {
            view.export("sh", control);
            view.redirect("iocaste-search-help", "main");
            view.setReloadableView(true);
        } else {
            if (control == null) {
                action = controlname;
            } else {
                action = control.getAction();
                if (control.isEventAware()) {
                    control.onEvent(EventAware.ON_CLICK, action);
                    return view;
                }
            }
            
            method = this.getClass().getMethod(action, ViewData.class);
            method.invoke(this, view);
            
            if (view.getMessageType() == Const.ERROR)
                new Iocaste(this).rollback();
        }
        
        return view;
    }
    
    /**
     * 
     * @param name
     * @return
     * @throws Exception 
     */
    protected final ViewData getView(ViewData view, String name)
            throws Exception {
        return new Shell(this).getView(view, name);
    }
    
    /**
     * 
     * @param message
     * @return
     * @throws Exception
     */
    public final ViewData getViewData(Message message) throws Exception {
        MessageSource messages;
        Method method;
        ViewData view;
        String page = message.getString("page");
        String app = message.getString("app");
        Locale locale = message.get("locale");
        @SuppressWarnings("unchecked")
        Map<String, Object> parameters =
                (Map<String, Object>)message.get("parameters");
        
        if (app == null || page == null)
            throw new IocasteException("page not especified.");
        
        view = new ViewData(app, page);
        view.setLocale(locale);
        
        for (String name : parameters.keySet())
            view.addParameter(name, parameters.get(name));
        
        method = this.getClass().getMethod(page, ViewData.class);
        method.invoke(this, view);
        
        if (view.getMessages() == null) {
            messages = new MessageSource();
            messages.loadFromApplication(app, locale, this);
            view.setMessages(messages);
        }
        
        return view;
    }
    
    /**
     * 
     * @param view
     */
    public void help(ViewData view) { }
    
    /**
     * 
     * @param view
     */
    public void home(ViewData view) { }
    
    /**
     * 
     * @param view
     * @throws Exception
     */
    protected final void updateView(ViewData view) throws Exception {
        new Shell(this).updateView(view);
    }
}