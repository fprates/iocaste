package org.iocaste.shell.common;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
    private AbstractContext context;
    private Map<String, ViewCustomAction> customactions;
    private Map<String, CustomView> customviews;
    private Map<String, Validator> validators;
    private Map<String, List<String>> validables;
    private ViewState state;
    
    public AbstractPage() {
        export("get_view_data", "getViewData");
        export("exec_action", "execAction");
        customactions = new HashMap<>();
        customviews = new HashMap<>();
        validators = new HashMap<>();
        validables = new HashMap<>();
        state = new ViewState();
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
    
    public final void exec(String app, String page) {
        context.view.setReloadableView(true);
        context.view.redirect(app, page, View.INITIALIZE);
    }
    
    /**
     * Executa métodos associados à action.
     * @param message
     * @return
     * @throws Exception
     */
    public final ViewState execAction(Message message) throws Exception {
        Validator validator;
        InputComponent input;
        List<String> handlers;
        ViewCustomAction customaction;
        Method method;
        String action, error;
        String controlname = message.getString("action");
        View view = message.get("view");
        ControlComponent control = view.getElement(controlname);
        
        if (context != null) {
            context.view = view;
            context.function = this;
        }
        
        state.view = view;
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
            method = getClass().getMethod(action);
            method.invoke(this);
        }
        
        return state;
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
        CustomView customview;
        View view = message.get("view");
        boolean initializable = message.getbool("init");
        Iocaste iocaste = new Iocaste(this);
        Locale locale = iocaste.getLocale();

        state.keepview = false;
        if (context != null)
            context.view = view;
        
        view.setLocale(locale);
        if (initializable) {
            customactions.clear();
            customviews.clear();
            validators.clear();
            context = init(view);
            context.view = view;
            context.function = this;
        }
        
        customview = customviews.get(view.getPageName());
        if (customview != null) {
            customview.execute(context);
        } else {
            method = getClass().getMethod(view.getPageName());
            method.invoke(this);
        }
        
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
    protected abstract AbstractContext init(View view) throws Exception;
    
    /**
     * 
     */
    public final void keepView() {
        state.keepview = true;
    }
    
    /**
     * 
     * @param view
     * @param custom
     */
    public final void register(String view, CustomView custom) {
        customviews.put(view, custom);
    }
    
    /**
     * 
     * @param name
     * @param validator
     */
    public final void register(String name, Validator validator) {
        validators.put(name, validator);
    }
    
    /**
     * 
     * @param action
     * @param custom
     */
    public final void register(String action, ViewCustomAction custom) {
        customactions.put(action, custom);
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
     * Atualiza uma visão, não necessariamente a visão atual.
     * @param view visão a ser atualizada.
     */
    protected final void updateView(View view) {
        new Shell(this).updateView(view);
    }
    
    /**
     * 
     * @param input
     * @param validator
     */
    public final void validate(InputComponent input, String validator) {
        validate(input.getHtmlName(), validator);
    }
    
    /**
     * 
     * @param input
     * @param validator
     */
    public final void validate(String input, String validator) {
        List<String> validators = validables.get(input);
        
        if (validators == null) {
            validators = new ArrayList<>();
            validables.put(input, validators);
        }
        
        validators.add(validator);       
    }
}