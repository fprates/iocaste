package org.iocaste.protocol;

import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import org.iocaste.protocol.user.Authorization;
import org.iocaste.protocol.user.User;

public final class Iocaste extends AbstractServiceInterface {
    public static final String SERVERNAME = "/iocaste-core/service.html";
    
    public Iocaste(Function function) {
        initService(function, SERVERNAME);
    }
    
    /**
     * 
     * @param sql
     * @param in
     * @param out
     * @return
     */
    public final Object callProcedure(String sql, Map<String, Object> in,
            Map<String, Integer> out) {
        Message message = new Message();
        
        message.setId("call_procedure");
        message.add("sql", sql);
        message.add("in", in);
        message.add("out", out);
        return call(message);
    }
    
    /**
     * Aplica commit work à uma transação de banco de dados.
     */
    public final void commit() {
        Message message = new Message();
        
        message.setId("commit");
        call(message);
    }
    
    /**
     * Criar usuário.
     * @param userdata nome do usuário
     */
    public final void create(User user) {
        Message message = new Message();
        
        message.setId("create_user");
        message.add("userdata", user);
        call(message);
    }
    
    /**
     * Desconecta usuário atual da sessão.
     */
    public final void disconnect() {
        Message message = new Message();
        
        message.setId("disconnect");
        call(message);
    }
    
    /**
     * Retorna objeto do contexto informado.
     * @param name nome do contexto
     * @return contexto
     */
    public final Object getContext(String name) {
        Message message = new Message();
        
        message.setId("get_context");
        message.add("context_id", name);
        return call(message);
    }
    
    /**
     * 
     * @return
     */
    public final String getCurrentApp() {
        Message message = new Message();
        
        message.setId("get_current_app");
        return call(message);
    }
    
    /**
     * 
     * @return
     */
    public final String getHost() {
        Message message = new Message();
        
        message.setId("get_host");
        return call(message);
    }
    
    /**
     * 
     * @return
     */
    public final Locale getLocale() {
        Message message = new Message();
        
        message.setId("get_locale");
        return call(message);
    }
    
    /**
     * 
     * @param username
     * @return
     */
    public final Map<String, Object> getSessionInfo(String sessionid) {
        Message message = new Message();
        
        message.setId("get_session_info");
        message.add("sessionid", sessionid);
        return call(message);
    }
    
    /**
     * Retorna sessões ativas
     * @return
     */
    public final String[] getSessions() {
        Message message = new Message();
        
        message.setId("get_sessions");
        return call(message);
    }
    
    /**
     * 
     * @return
     */
    public final Properties getSystemInfo() {
        Message message = new Message();
        
        message.setId("get_system_info");
        return call(message);
    }
    
    /**
     * 
     * @param name
     * @return
     */
    public final String getSystemParameter(String name) {
        Message message = new Message();
        
        message.setId("get_system_parameter");
        message.add("parameter", name);
        return call(message);
    }
    
    /**
     * Retorna usuário da sessão.
     * @return
     */
    public final String getUsername() {
        Message message = new Message();
        
        message.setId("get_username");
        return call(message);
    }
    
    /**
     * 
     */
    public final void invalidateAuthCache() {
        Message message = new Message();
        
        message.setId("invalidate_auth_cache");
        call(message);
    }
    
    /**
     * 
     * @param authorization
     * @return
     */
    public final boolean isAuthorized(Authorization authorization) {
        Message message = new Message();
        
        message.setId("is_authorized");
        message.add("authorization", authorization);
        return call(message);
    }
    
    /**
     * Verifica se há usuário conectado a sessão atual.
     * @return true, se usuário está conectado.
     */
    public final boolean isConnected() {
        Message message = new Message();
        
        message.setId("is_connected");
        return call(message);
    }
    
    /**
     * Conecta usuário à sessão.
     * @param user nome do usuário
     * @param secret senha
     * @param locale
     * @return true, se a conexão foi bem sucedida
     */
    public final boolean login(String user, String secret, String locale) {
        Message message = new Message();
        
        message.setId("login");
        message.add("user", user);
        message.add("secret", secret);
        message.add("locale", locale);
        return call(message);
    }
    
    /**
     * 
     */
    public final void rollback() {
        Message message = new Message();
        
        message.setId("rollback");
        call(message);
    }
    
    /**
     * Seleciona itens de tabela através de query.
     * @param query seleção
     * @param criteria critérios da seleção
     * @return resultados
     */
    public final Object[] select(String query, Object... criteria) {
        return selectUpTo(query, 0, criteria);
    }
    
    /**
     * Seleciona itens de tabela através de query.
     * @param query seleção
     * @param criteria critérios da seleção
     * @return resultados
     */
    public final Object[] selectUpTo(String query, int rows, Object... criteria) 
    {
        Message message = new Message();
        
        message.setId("select");
        message.add("query", query);
        message.add("criteria", criteria);
        message.add("rows", rows);
        return call(message);
    }
    
    /**
     * Armazena objeto em contexto identificado.
     * @param name nome do contexto
     * @param object objeto a ser armazenado
     */
    public final void setContext(String name, Object object) {
        Message message = new Message();
        
        message.setId("set_context");
        message.add("context_id", name);
        message.add("object", object);
        call(message);
    }
    
    /**
     * 
     * @param user
     */
    public final void update(User user) {
        Message message = new Message();
        
        message.setId("update_user");
        message.add("user", user);
        call(message);
    }
    
    /**
     * 
     * @param query
     * @return
     */
    public final int update(String query, Object... criteria) {
    	Message message = new Message();
    	
    	message.setId("update");
    	message.add("query", query);
    	message.add("criteria", criteria);
    	return call(message);
    }
}
