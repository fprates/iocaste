package org.iocaste.protocol;

import org.iocaste.protocol.user.User;

public final class Iocaste extends AbstractServiceInterface {
    public static final String SERVERNAME = "/iocaste-core/service.html";
    
    public Iocaste(Function function) {
        initService(function, SERVERNAME);
    }
    
    /**
     * Criar usuário.
     * @param userdata nome do usuário
     * @throws Exception
     */
    public final void createUser(User user) throws Exception {
        Message message = new Message();
        
        message.setId("create_user");
        message.add("userdata", user);
        
        call(message);
    }
    
    /**
     * Desconecta usuário atual da sessão.
     * @throws Exception
     */
    public final void disconnect() throws Exception {
        Message message = new Message();
        
        message.setId("disconnect");
        
        call(message);
    }
    
    /**
     * Retorna objeto do contexto informado.
     * @param name nome do contexto
     * @return contexto
     * @throws Exception
     */
    public final Object getContext(String name) throws Exception {
        Message message = new Message();
        
        message.setId("get_context");
        message.add("context_id", name);
        
        return call(message);
    }
    
    /**
     * Retorna usuário da sessão.
     * @return
     * @throws Exception
     */
    public final String getUsername() throws Exception {
        Message message = new Message();
        
        message.setId("get_username");
        
        return (String)call(message);
    }
    
    /**
     * Retorna usuários.
     * @return array de usuários
     * @throws Exception
     */
    public final User[] getUsers() throws Exception {
        Message message = new Message();
        
        message.setId("get_users");
        
        return (User[])call(message);
    }
    
    /**
     * Verifica se há usuário conectado a sessão atual.
     * @return true, se usuário está conectado.
     * @throws Exception
     */
    public final boolean isConnected() throws Exception {
        Message message = new Message();
        
        message.setId("is_connected");
        
        return (Boolean)call(message);
    }
    
    /**
     * Conecta usuário à sessão.
     * @param user nome do usuário
     * @param secret senha
     * @return true, se a conexão foi bem sucedida
     * @throws Exception
     */
    public final boolean login(String user, String secret) throws Exception {
        Message message = new Message();
        
        message.setId("login");
        message.add("user", user);
        message.add("secret", secret);
        
        return (Boolean)call(message);
    }
    
    /**
     * Seleciona itens de tabela através de query.
     * @param query seleção
     * @param criteria critérios da seleção
     * @return resultados
     * @throws Exception
     */
    public final Object[] select(String query, Object[] criteria) 
            throws Exception {
        Message message = new Message();
        
        message.setId("select");
        message.add("query", query);
        message.add("criteria", criteria);
        
        return (Object[])call(message);
    }
    
    /**
     * Armazena objeto em contexto identificado.
     * @param name nome do contexto
     * @param object objeto a ser armazenado
     * @throws Exception
     */
    public final void setContext(String name, Object object) throws Exception {
        Message message = new Message();
        
        message.setId("set_context");
        message.add("context_id", name);
        message.add("object", object);
        
        call(message);
    }
    
    /**
     * 
     * @param query
     * @return
     * @throws Exception
     */
    public final int update(String query, Object[] criteria) throws Exception {
    	Message message = new Message();
    	
    	message.setId("update");
    	message.add("query", query);
    	message.add("criteria", criteria);
    	
    	return (Integer)call(message);
    }
}
