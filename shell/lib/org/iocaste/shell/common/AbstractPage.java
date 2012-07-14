package org.iocaste.shell.common;

import java.lang.reflect.Method;
import java.util.Locale;
import java.util.Map;

import org.iocaste.protocol.AbstractFunction;
import org.iocaste.protocol.Iocaste;
import org.iocaste.protocol.IocasteException;
import org.iocaste.protocol.Message;

/**
 * Implementação abstrata de página web
 * 
 * Para implementações que exigem interação com usuário, este componente
 * envia response através de View e executa métodos chamados via request.
 * 
 * @author francisco.prates
 *
 */
public abstract class AbstractPage extends AbstractFunction {
    
    public AbstractPage() {
        export("get_view_data", "getViewData");
        export("exec_action", "execAction");
        export("custom_validation", "customValidation");
    }
    
    /**
     * Retorna a página anterior.
     * @param view visão atual
     */
    public void back(View view) {
        String[] entry;
        try {
            entry = new Shell(this).popPage(view);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        
        view.redirect(entry[0], entry[1]);
        view.dontPushPage();
        
        if (view.getAppName().equals(entry[0]))
            return;
        
        view.setReloadableView(true);
    }
    
    /**
     * Chamado quando tem validação ajustada via setValidator().
     * @param message
     * @return
     */
    public final ValidatorConfig customValidation(Message message)
            throws Exception {
        ValidatorConfig config = message.get("config");
        Validator validator = (Validator)Class.forName(config.getClassName()).
                newInstance();
        
        validator.setFunction(this);
        config.setMessage(null);
        validator.validate(config);
        return config;
    }
    
    /**
     * Executa métodos associados à action.
     * @param message
     * @return
     * @throws Exception
     */
    public final View execAction(Message message) throws Exception {
        Method method;
        View view = message.get("view");
        String action, controlname = message.getString("action");
        ControlComponent control = view.getElement(controlname);
        
        /*
         * TODO poderia ser feito algo melhor do que este hardcode?
         */
        if (control != null && control.getType() == Const.SEARCH_HELP) {
            view.setParameter("sh", control);
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
            
            method = this.getClass().getMethod(action, View.class);
            method.invoke(this, view);
        }
        
        return view;
    }
    
    /**
     * Retorna visão especificada.
     * @param view visão atual
     * @param name identificador da visão
     * @return visão
     * @throws Exception
     */
    protected final View getView(View view, String name)
            throws Exception {
        return new Shell(this).getView(view, name);
    }
    
    /**
     * Gera uma visão solicitada por redirect().
     * @param message
     * @return
     * @throws Exception
     */
    public final View getViewData(Message message) throws Exception {
        MessageSource messages;
        Method method;
        View view;
        Locale locale;
        String page = message.getString("page");
        String app = message.getString("app");
        Map<String, Object> parameters = message.get("parameters");
        
        /*
         * TODO pode ser movido para o servidor
         */
        if (app == null || page == null)
            throw new IocasteException("page not especified.");
        
        locale = new Iocaste(this).getLocale();
        view = new View(app, page);
        view.setLocale(locale);
        
        for (String name : parameters.keySet())
            view.export(name, parameters.get(name));
        
        method = this.getClass().getMethod(page, View.class);
        method.invoke(this, view);
        if (view.getMessages() == null) {
            messages = new MessageSource();
            messages.loadFromApplication(app, locale, this);
            view.setMessages(messages);
        }
        
        return view;
    }
    
    /**
     * Chama visão de ajuda.
     * @param view
     */
    public void help(View view) { }
    
    /**
     * Retorna à página inicial.
     * @param view visão atual
     * @throws Exception
     */
    public void home(View view) throws Exception {
        String[] entry = new Shell(this).home(view);
        view.redirect(entry[0], entry[1]);
        view.dontPushPage();
        view.setReloadableView(true);
    }
    
    /**
     * Atualiza uma visão, não necessariamente a visão atual.
     * @param view visão a ser atualizada.
     * @throws Exception
     */
    protected final void updateView(View view) throws Exception {
        new Shell(this).updateView(view);
    }
}