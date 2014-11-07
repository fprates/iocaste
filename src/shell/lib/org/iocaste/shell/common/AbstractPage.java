package org.iocaste.shell.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.iocaste.protocol.AbstractFunction;
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
        GetViewData getviewdata;
        ExecAction execaction;
        
        customactions = new HashMap<>();
        customviews = new HashMap<>();
        validators = new HashMap<>();
        validables = new HashMap<>();
        state = new ViewState();
        
        getviewdata = new GetViewData();
        getviewdata.customviews = customviews;
        getviewdata.customactions = customactions;
        getviewdata.validators = validators;
        getviewdata.validables = validables;
        
        execaction = new ExecAction();
        execaction.customactions = customactions;
        execaction.validators = validators;
        execaction.validables = validables;
        
        export("get_view_data", getviewdata);
        export("exec_action", execaction);
    }
    
    /**
     * Retorna a página anterior.
     * @param view visão atual
     */
    public void back() {
        PageStackItem entry = new Shell(this).popPage(context.view);
        redirect(entry.getApp(), entry.getPage(), false);
        dontPushPage();
    }
    
    /**
     * Retorna à página especificada
     * @param position página
     */
    public final void backTo(String position) {
        String[] entry = position.split("\\.");
        
        new Shell(this).setPagesPosition(position);
        redirect(entry[0], entry[1], false);
        dontPushPage();
    }
    
    /**
     * 
     */
    public final void clearExports() {
        state.parameters.clear();
    }
    
    /**
     * Não salva página na pilha de chamada.
     */
    public final void dontPushPage() {
        state.dontpushpage = true;
        state.pagecall = false;
    }
    
    public final void exec(String app, String page) {
        state.reloadable = true;
        redirect(app, page, View.INITIALIZE);
    }
    
    /**
     * Exporta parâmetro para próxima visão.
     * @param name nome
     * @param value valor
     */
    public final void export(String name, Object value) {
        state.parameters.put(name, value);
    }
    
    @SuppressWarnings("unchecked")
    public final <T> T getParameter(String name) {
        return (T)state.parameters.get(name);
    }
    
    public final String getRedirectedApp() {
        return state.rapp;
    }
    
    public final String getRedirectedPage() {
        return state.rpage;
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
        redirect(entry.getApp(), entry.getPage(), false);
        dontPushPage();
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
    
    public final void redirect(String page) {
        redirect(null, page, false);
    }
    
    /**
     * Redireciona aplicação e visão.
     * @param app aplicação
     * @param page página
     * @param initialize true, para inicializar a visão.
     */
    private final void redirect(String app, String page, boolean initialize) {
        state.rapp = app;
        state.rpage = page;
        state.initialize = initialize;
        
        if (!state.dontpushpage)
            state.pagecall = true;
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
    
    @Override
    public final Object run(Message message) throws Exception {
        GetViewData getviewdata;
        ExecAction execaction;
        Object object;
        String id = message.getId();
        
        if (!id.equals("get_view_data"))
            return run(message);
        
        getviewdata = get(id);
        object = getviewdata.run(message);
        if (context == null) {
            context = getviewdata.context;
            execaction = get("exec_action");
            execaction.context = context;
        }
        
        return object;
    }
    
    /**
     * Define parâmetro para header http.
     * @param key nome
     * @param value valor
     */
    public final void setHeader(String key, String value) {
        state.headervalues.put(key, value);
    }
    
    public final void setReloadableView(boolean reloadable) {
        state.reloadable = reloadable;
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