package org.iocaste.protocol;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.iocaste.protocol.files.Directory;
import org.iocaste.protocol.files.FileEntry;
import org.iocaste.protocol.user.Authorization;
import org.iocaste.protocol.user.User;

public final class Iocaste extends AbstractServiceInterface {
    public static final String SERVERNAME = "/iocaste-kernel/service.html";
    public static final int CREATE = 0;
    public static final int READ = 1;
    public static final int WRITE = 2;
    public static final byte RAW = 0;
    public static final byte JAR = 1;
    public static final byte OUT_PRINT = 0;
    public static final byte ERR_PRINT = 1;
    public static final byte ERR_CODE = 2;
    public static final byte DISCONNECT_EVENT = 0;
    
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
    
    public final void close(String id) {
        Message message = new Message("file_close");
        message.add("id", id);
        call(message);
    }
    
    /**
     * Aplica commit work à uma transação de banco de dados.
     */
    public final void commit() {
        call(new Message("commit"));
    }
    
    public final String compile(String project, Directory source) {
        Message message = new Message("compile");
        message.add("project", project);
        message.add("source", source);
        return call(message);
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
    
    public final boolean delete(String... args) {
        return delete(false, args);
    }
    
    public final void delete(FileEntry[] files) {
        Message message = new Message("file_entries_delete");
        message.add("files", files);
        call(message);
    }
    
    private final boolean delete(boolean all, String... args) {
        Message message = new Message("file_delete");
        message.add("args", args);
        message.add("all", all);
        return call(message);
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
     * 
     * @param program
     * @param args
     * @return
     */
    public final Object[] execute(String program, String... args) {
        Message message = new Message("external_process_execute");
        message.add("program", program);
        message.add("args", args);
        return call(message);
    }
    
    /**
     * 
     * @param args
     * @return
     */
    public final boolean exists(String... args) {
        Message message = new Message("file_exists");
        message.add("args", args);
        return call(message);
    }
    
    /**
     * 
     * @param option
     * @param args
     * @return
     */
    public final String file(int option, String... args) {
        Message message = new Message("file");
        message.add("option", option);
        message.add("args", args);
        return call(message);
    }
    
    /**
     * 
     * @return
     */
    public final String[] getAvailablePackages() {
        Message message = new Message("packages_get");
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
     * @param args
     * @return
     */
    public final FileEntry[] getFiles(String... args) {
        Message message = new Message("files_get");
        message.add("args", args);
        return call(message);
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
     * @param file
     * @return
     */
    public final byte[] getMetaContext(String pkgname, String file) {
        Message message = new Message("meta_context_get");
        message.add("package", pkgname);
        message.add("file", file);
        return call(message);
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
    public final Set<String> getSessions() {
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
    
    public final void mkdir(String... args) {
        Message message = new Message("mkdir");
        message.add("args", args);
        call(message);
    }
    
    public final byte[] read(String id) {
        Message message = new Message("rfile_ead");
        message.add("id", id);
        return call(message);
    }
    
    public final boolean rmdir(String... args) {
        return delete(true, args);
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
     * @param username
     * @param secret
     * @param initial
     */
    public final void setUserPassword(
            String username, String secret, boolean initial) {
        Message message = new Message("set_user_password");
        message.add("username", username);
        message.add("secret", secret);
        message.add("initial", initial);
        call(message);
    }
    
    public final List<FileEntry> unzip(String target, String... source) {
        Message message = new Message("file_unzip");
        message.add("target", target);
        message.add("source", source);
        return call(message);
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
    
    public final void write(String id, byte[] buffer) {
        Message message = new Message("file_write");
        message.add("id", id);
        message.add("buffer", buffer);
        call(message);
    }
    
    public final void write(String dirsymbol, Directory directory, byte type) {
        Message message = new Message("directory_write");
        message.add("symbol", dirsymbol);
        message.add("directory", directory);
        message.add("type", type);
        call(message);
    }
}
