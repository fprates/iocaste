package org.iocaste.kernel.common;

import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import org.iocaste.kernel.common.user.Authorization;
import org.iocaste.kernel.common.user.User;
import org.iocaste.protocol.AbstractServiceInterface;
import org.iocaste.protocol.Function;
import org.iocaste.protocol.Message;

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
        Message message = new Message("call_procedure");
        message.add("sql", sql);
        message.add("in", in);
        message.add("out", out);
        return call(message);
    }
    
    /**
     * Aplica commit work à uma transação de banco de dados.
     */
    public final void commit() {
        call(new Message("commit"));
    }
    
    /**
     * Criar usuário.
     * @param userdata nome do usuário
     */
    public final void create(User user) {
        Message message = new Message("create_user");
        message.add("userdata", user);
        call(message);
    }
    
    /**
     * Desconecta usuário atual da sessão.
     */
    public final void disconnect() {
        call(new Message("disconnect"));
    }
    
    /**
     * 
     * @param username
     */
    public final void dropUser(String username) {
        Message message = new Message("drop_user");
        message.add("username", username);
        call(message);
    }
    
    /**
     * Retorna objeto do contexto informado.
     * @param name nome do contexto
     * @return contexto
     */
    public final Object getContext(String name) {
        Message message = new Message("get_context");
        message.add("context_id", name);
        return call(message);
    }
    
    /**
     * 
     * @return
     */
    public final String getCurrentApp() {
        return call(new Message("get_current_app"));
    }
    
    /**
     * 
     * @return
     */
    public final String getHost() {
        return call(new Message("get_host"));
    }
    
    /**
     * 
     * @return
     */
    public final Locale getLocale() {
        return call(new Message("get_locale"));
    }
    
    /**
     * 
     * @param username
     * @return
     */
    public final Map<String, Object> getSessionInfo(String sessionid) {
        Message message = new Message("get_session_info");
        message.add("sessionid", sessionid);
        return call(message);
    }
    
    /**
     * Retorna sessões ativas
     * @return
     */
    public final String[] getSessions() {
        return call(new Message("get_sessions"));
    }
    
    /**
     * 
     * @return
     */
    public final Properties getSystemInfo() {
        return call(new Message("get_system_info"));
    }
    
    /**
     * 
     * @param name
     * @return
     */
    public final String getSystemParameter(String name) {
        Message message = new Message("get_system_parameter");
        message.add("parameter", name);
        return call(message);
    }
    
    /**
     * 
     * @param name
     * @return
     */
    public final User getUserData(String name) {
        Message message = new Message("get_user_data");
        message.add("username", name);
        return call(message);
    }
    
    /**
     * Retorna usuário da sessão.
     * @return
     */
    public final String getUsername() {
        return call(new Message("get_username"));
    }
    
    /**
     * 
     */
    public final void invalidateAuthCache() {
        call(new Message("invalidate_auth_cache"));
    }
    
    /**
     * 
     * @param authorization
     * @return
     */
    public final boolean isAuthorized(Authorization authorization) {
        Message message = new Message("is_authorized");
        message.add("authorization", authorization);
        return call(message);
    }
    
    /**
     * Verifica se há usuário conectado a sessão atual.
     * @return true, se usuário está conectado.
     */
    public final boolean isConnected() {
        return call(new Message("is_connected"));
    }
    
    /**
     * 
     * @return
     */
    public final boolean isInitialSecret() {
        return call(new Message("is_initial_secret"));
    }
    
    /**
     * Conecta usuário à sessão.
     * @param user nome do usuário
     * @param secret senha
     * @param locale
     * @return true, se a conexão foi bem sucedida
     */
    public final boolean login(String user, String secret, String locale) {
        Message message = new Message("login");
        message.add("user", user);
        message.add("secret", secret);
        message.add("locale", locale);
        return call(message);
    }
    
    /**
     * 
     */
    public final void rollback() {
        call(new Message("rollback"));
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
        Message message = new Message("select");
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
        Message message = new Message("set_context");
        message.add("context_id", name);
        message.add("object", object);
        call(message);
    }
    
    /**
     * 
     * @param secret
     */
    public final void setUserPassword(String secret) {
        Message message = new Message("set_user_password");
        message.add("secret", secret);
        call(message);
    }
    
    /**
     * 
     * @param user
     */
    public final void update(User user) {
        Message message = new Message("update_user");
        message.add("user", user);
        call(message);
    }
    
    /**
     * 
     * @param query
     * @return
     */
    public final int update(String query, Object... criteria) {
    	Message message = new Message("update");
    	message.add("query", query);
    	message.add("criteria", criteria);
    	return call(message);
    }
}
