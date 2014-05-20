package org.iocaste.shell.common;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

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
    private PageContext context;
    private Map<String, ViewCustomAction> customactions;
    
    public AbstractPage() {
        export("get_view_data", "getViewData");
        export("exec_action", "execAction");
        export("custom_validation", "customValidation");
        customactions = new HashMap<>();
    }
    
    /**
     * Retorna a página anterior.
     * @param view visão atual
     */
    public void back() {
        PageStackItem entry = new Shell(this).popPage(context.view);
        context.view.redirect(entry.getApp(), entry.getPage());
        context.view.dontPushPage();
    }
    
    /**
     * Retorna à página especificada
     * @param position página
     */
    public final void backTo(String position) {
        String[] entry = position.split("\\.");
        
        new Shell(this).setPagesPosition(position);
        context.view.redirect(entry[0], entry[1]);
        context.view.dontPushPage();
    }
    
    /**
     * Chamado quando tem validação ajustada via setValidator().
     * @param message
     * @return
     */
    public final ValidatorConfig customValidation(Message message)
            throws Exception {
        ValidatorConfig config = message.get("config");
        
        return validate(config);
    }
    
    /**
     * Executa métodos associados à action.
     * @param message
     * @return
     * @throws Exception
     */
    public final View execAction(Message message) throws Exception {
        ViewCustomAction customaction;
        Method method;
        View view = message.get("view");
        String action, controlname = message.getString("action");
        ControlComponent control = view.getElement(controlname);
        
        if (context != null) {
            context.view = view;
            context.function = this;
        }
        
        action = (control == null)? controlname : control.getName();
        view.setActionControl(action);
        validate();
        action = (control == null)? controlname : control.getAction();
        if (!action.equals("validate")) {
            customaction = customactions.get(action);
            if (customaction != null) {
                customaction.execute(context);
            } else {
                method = getClass().getMethod(action);
                method.invoke(this);
            }
        }
        
        return view;
    }
    
    /**
     * Retorna visão especificada.
     * @param name identificador da visão
     * @return visão
     */
    protected final View getView(String name) {
        return new Shell(this).getView(context.view, name);
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
        boolean initializable = message.getbool("init");
        Iocaste iocaste = new Iocaste(this);
        Locale locale = iocaste.getLocale();

        if (context != null)
            context.view = view;
        
        view.setLocale(locale);
        if (initializable) {
            context = init(view);
            context.view = view;
            context.function = this;
        }
        
        method = getClass().getMethod(view.getPageName());
        method.invoke(this);
        if (view.getMessages() == null) {
            /*
             * há alguma chance que getViewData() tenha sido chamada
             * a partir de um ticket, que nesse caso teria a localização
             * definida (provavelmente) apenas depois da chamada da visão.
             */
            if (locale == null) {
                locale = iocaste.getLocale();
                view.setLocale(locale);
                for (Container container : view.getContainers())
                    setLocaleForElement(container, view.getLocale());
            }
            
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
    public void help() { }
    
    /**
     * Retorna à página inicial.
     * @param view visão atual
     */
    public void home() {
        PageStackItem entry = new Shell(this).home(context.view);
        context.view.redirect(entry.getApp(), entry.getPage());
        context.view.dontPushPage();
    }
    
    /**
     * 
     * @param view
     * @throws Exception
     */
    protected abstract PageContext init(View view) throws Exception;
    
    /**
     * Atualiza uma visão, não necessariamente a visão atual.
     * @param view visão a ser atualizada.
     */
    protected final void updateView(View view) {
        new Shell(this).updateView(view);
    }
    
    private void setLocaleForElement(Element element, Locale locale) {
        Container container;
        
        element.setLocale(locale);
        if (!element.isContainable())
            return;
        
        container = (Container)element;
        for (Element element_ : container.getElements())
            setLocaleForElement(element_, locale);
    }
    
    /**
     * 
     * @param action
     * @param custom
     */
    public final void register(String action, ViewCustomAction custom) {
        if (customactions.containsKey(action))
            throw new RuntimeException(new StringBuilder("custom action '").
                    append(action).
                    append("' has already registered.").toString());
        
        customactions.put(action, custom);
    }
    
    protected void validate() { }
    
    /**
     * 
     * @param config
     * @return
     * @throws Exception
     */
    private final ValidatorConfig validate(ValidatorConfig config)
            throws Exception {
        Validator validator = (Validator)Class.forName(config.getClassName()).
                newInstance();
        
        validator.setFunction(this);
        config.setMessage(null);
        validator.validate(config);
        
        return config;
        
    }
    
    /**
     * 
     * @param input
     * @return
     */
    public final ValidatorConfig validate(InputComponent input) {
        ValidatorConfig config = input.getValidatorConfig();
        
        try {
            return validate(config);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}