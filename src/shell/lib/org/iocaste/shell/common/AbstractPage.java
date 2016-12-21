package org.iocaste.shell.common;

import java.util.HashMap;
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
    private GetViewData getviewdata;
    
    public AbstractPage() {
        ExecAction execaction;
        
        getviewdata = new GetViewData();
        getviewdata.state = new ViewState();
        getviewdata.customviews = new HashMap<>();
        getviewdata.customactions = new HashMap<>();
        
        execaction = new ExecAction();
        execaction.state = getviewdata.state;
        execaction.customactions = getviewdata.customactions;
        
        export("get_view_data", getviewdata);
        export("exec_action", execaction);
    }
    
    /**
     * Retorna a página anterior.
     * @param view visão atual
     */
    public void back() {
        PageStackItem entry = new Shell(this).popPage();
        if (entry == null)
            return;
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
        getviewdata.state.parameters.clear();
    }
    
    /**
     * Não salva página na pilha de chamada.
     */
    public final void dontPushPage() {
        getviewdata.state.dontpushpage = true;
        getviewdata.state.pagecall = false;
    }
    
    public final void download() {
        getviewdata.state.download = true;
    }
    
    public final void exec(String app, String page) {
        getviewdata.state.reloadable = true;
        redirect(app, page, View.INITIALIZE);
    }
    
    /**
     * Exporta parâmetro para próxima visão.
     * @param name nome
     * @param value valor
     */
    public final void export(String name, Object value) {
        getviewdata.state.parameters.put(name, value);
    }
    
    public final Const getMessageType() {
        return getviewdata.state.messagetype;
    }
    
    @SuppressWarnings("unchecked")
    public final <T> T getParameter(String name) {
        return (T)getviewdata.state.parameters.get(name);
    }
    
    public final Map<String, Object> getParameters() {
        return getviewdata.state.parameters;
    }
    
    public final int getPort() {
        return getviewdata.state.port;
    }
    
    public final String getProtocol() {
        return getviewdata.state.protocol;
    }
    
    public final String getRedirectedApp() {
        return getviewdata.state.rapp;
    }
    
    public final String getRedirectedPage() {
        return getviewdata.state.rpage;
    }
    
    public final String getServername() {
        return getviewdata.state.servername;
    }
    
    /**
     * Retorna visão especificada.
     * @param name identificador da visão
     * @return visão
     */
    protected final View getView(String name) {
        return new Shell(this).getView(getviewdata.context.view, name);
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
        home(null);
    }
    
    /**
     * Retorna à página inicial.
     * @param view visão atual
     */
    public void home(String page) {
        PageStackItem entry = new Shell(this).home(page);
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
        getviewdata.state.keepview = true;
    }
    
    /**
     * Ajusta tipo e texto da mensagem.
     * @param messagetype tipo
     * @param messagetext texto
     */
    public final void message(
            Const messagetype, String messagetext, Object... args) {
        getviewdata.state.messagetype = messagetype;
        getviewdata.state.messagetext = messagetext;
        getviewdata.state.messageargs = args;
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
        getviewdata.state.rapp = app;
        getviewdata.state.rpage = page;
        getviewdata.state.initialize = initialize;
        
        if (!getviewdata.state.dontpushpage)
            getviewdata.state.pagecall = true;
    }
    
    /**
     * 
     * @param view
     * @param custom
     */
    public final void register(String view, CustomView custom) {
        getviewdata.customviews.put(view, custom);
    }
    
    /**
     * 
     * @param action
     * @param custom
     */
    public final void register(String action, ViewCustomAction custom) {
        getviewdata.customactions.put(action, custom);
    }
    
    @Override
    public final Object run(Message message) throws Exception {
        ExecAction execaction;
        String id = message.getId();
        
        getviewdata.state.messagetext = null;
        getviewdata.state.messagetype = Const.NONE;
        getviewdata.state.download = false;
        if (!id.equals("exec_action"))
            return super.run(message);

        execaction = get(id);
        execaction.context = getviewdata.context;
        return execaction.run(message);
    }
    
    public final void setContentEncoding(String contentencoding) {
        getviewdata.state.contentencoding = contentencoding;
    }
    
    public final void setContentType(String contenttype) {
        getviewdata.state.contenttype = contenttype;
    }
    
    /**
     * Define parâmetro para header http.
     * @param key nome
     * @param value valor
     */
    public final void setHeader(String key, String value) {
        getviewdata.state.headervalues.put(key, value);
    }
    
    /**
     * 
     * @param reloadable
     */
    public final void setReloadableView(boolean reloadable) {
        getviewdata.state.reloadable = reloadable;
    }
    
    /**
     * Atualiza uma visão, não necessariamente a visão atual.
     * @param view visão a ser atualizada.
     */
    protected final void updateView(View view) {
        new Shell(this).updateView(view);
    }
}