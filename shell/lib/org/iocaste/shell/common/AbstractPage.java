package org.iocaste.shell.common;

import java.lang.reflect.Method;
import java.util.Locale;

import org.iocaste.protocol.AbstractFunction;
import org.iocaste.protocol.Iocaste;
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
        
        entry = new Shell(this).popPage(view);
        view.redirect(entry[0], entry[1]);
        view.dontPushPage();
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
        
        action = (control == null)? controlname : control.getAction();
        method = getClass().getMethod(action, View.class);
        method.invoke(this, view);
        return view;
    }
    
    /**
     * Retorna visão especificada.
     * @param view visão atual
     * @param name identificador da visão
     * @return visão
     */
    protected final View getView(View view, String name) {
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
        View view = message.get("view");
        Locale locale = new Iocaste(this).getLocale();
        
        view.setLocale(locale);
        init(view);
        method = getClass().getMethod(view.getPageName(), View.class);
        method.invoke(this, view);
        if (view.getMessages() == null) {
            messages = new MessageSource();
            messages.loadFromApplication(view.getAppName(), locale, this);
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
     */
    public void home(View view) {
        String[] entry = new Shell(this).home(view);
        view.redirect(entry[0], entry[1]);
        view.dontPushPage();
    }
    
    /**
     * 
     * @param view
     * @throws Exception
     */
    protected void init(View view) throws Exception { }
    
    /**
     * Atualiza uma visão, não necessariamente a visão atual.
     * @param view visão a ser atualizada.
     */
    protected final void updateView(View view) {
        new Shell(this).updateView(view);
    }
}