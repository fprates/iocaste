package org.iocaste.runtime.common;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.iocaste.documents.common.ComplexDocument;
import org.iocaste.documents.common.ComplexModel;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.documents.common.Query;
import org.iocaste.protocol.Message;
import org.iocaste.protocol.database.ConnectionInfo;
import org.iocaste.protocol.files.Directory;
import org.iocaste.protocol.files.FileEntry;
import org.iocaste.protocol.user.Authorization;
import org.iocaste.protocol.user.User;
import org.iocaste.protocol.user.UserProfile;
import org.iocaste.protocol.utils.ConversionResult;
import org.iocaste.protocol.utils.ConversionRules;
import org.iocaste.runtime.common.application.ViewExport;
import org.iocaste.runtime.common.protocol.AbstractRuntimeInterface;
import org.iocaste.runtime.common.protocol.ServiceInterfaceData;
import org.iocaste.runtime.common.protocol.ServiceUrl;
import org.iocaste.shell.common.tooldata.ToolData;

@ServiceUrl("/iocaste-kernel/service.html")
public class RuntimeEngine extends AbstractRuntimeInterface {
    public static final String SERVICE_URL = "/iocaste-kernel/service.html";
    
    public RuntimeEngine(ServiceInterfaceData data) {
    	initService(data);
    }
    
    /**
     * Adds authorization to a profile
     * @param profile authorizations' profile
     * @param authorization authorization
     */
    public final void addAuthorization(String profile,
            Authorization authorization) {
        Message message = new Message("add_auth");
        message.add("profile", profile);
        message.add("authorization", authorization);
        call(message);
    }
    
    /**
     * 
     * @param username
     * @param profile
     */
    public final void assignAuthorization(String username, String profile) {
        Message message = new Message("assign_auth_profile");
        message.add("username", username);
        message.add("profile", profile);
        call(message);
    }
    
    /**
     * 
     * @param username
     * @param profile
     * @param authorization
     */
    public final void assignAuthorization(String username, String profile,
            Authorization authorization) {
        Message message = new Message("assign_auth");
        message.add("username", username);
        message.add("profile", profile);
        message.add("authorization", authorization);
        call(message);
    }
    
    /**
     * Aplica commit work à uma transação de banco de dados.
     */
    public final void commit() {
        call(new Message("commit"));
    }
    
    /**
     * 
     * @param xml
     * @return
     */
    public final ConversionResult conversion(String xml) {
        return conversion(xml, null);
    }
    
    /**
     * 
     * @param xml
     * @param data
     * @return
     */
    public final ConversionResult conversion(String xml, ConversionRules data) {
        Message message = new Message("conversion");
        message.add("xml", xml);
        message.add("data", data);
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
    
    /**
     * 
     * @param name
     */
    public final void createNumberFactory(String name) {
        createNumberFactory(name, null, null);
    }
    
    /**
     * 
     * @param name
     * @param ns
     */
    public final void createNumberFactory(String name, Object ns) {
        createNumberFactory(name, ns, null);
    }
    
    /**
     * 
     * @param name
     * @param ns
     * @param series
     */
    public final void createNumberFactory(
            String name, Object ns, Map<String, Long> series) {
        Message message = new Message("create_number_factory");
        message.add("name", name);
        message.add("series", series);
        message.add("ns", ns);
        call(message);
    }
    
    public final void createText(String name) {
        Message message = new Message("text_register");
        message.add("name", name);
        call(message);
    }
    
    /**
     * Remove entrada da tabela.
     * 
     * A entrada é especificada por objeto extendido.
     * 
     * @param object objeto extendido
     * @return 1, para remoção bem sucedida.
     */
    public final int delete(ExtendedObject object) {
        Message message = new Message("delete_document");
        message.add("object", object);
        return call(message);
    }
    
    public final void deleteComplexDocument(String cmodelname, Object key) {
        deleteComplexDocument(cmodelname, null, key);
    }
    
    public final void deleteComplexDocument(
            String cmodelname, Object ns, Object key) {
        Message message = new Message("delete_complex_document");
        message.add("cmodel_name", cmodelname);
        message.add("ns", ns);
        message.add("id", key);
        call(message);
    }
    
    /**
     * Desconecta usuário atual da sessão.
     */
    public final void disconnect() {
        call(new Message("disconnect"));
    }
    
    public final void fclose(String id) {
        Message message = new Message("file_close");
        message.add("id", id);
        call(message);
    }
    
    public final void fdelete(FileEntry[] files) {
        Message message = new Message("file_entries_delete");
        message.add("files", files);
        call(message);
    }
    
    private final boolean fdelete(boolean all, String... args) {
        Message message = new Message("file_delete");
        message.add("args", args);
        message.add("all", all);
        return call(message);
    }
    
    public final boolean fdelete(String... args) {
        return fdelete(false, args);
    }
    
    /**
     * 
     * @param args
     * @return
     */
    public final boolean fexists(String... args) {
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
    
    public final byte[] fread(String id) {
        Message message = new Message("file_read");
        message.add("id", id);
        return call(message);
    }
    
    public final List<FileEntry> funzip(String target, String... source) {
        Message message = new Message("file_unzip");
        message.add("target", target);
        message.add("source", source);
        return call(message);
    }
    
    public final void fwrite(String id, byte[] buffer) {
        Message message = new Message("file_write");
        message.add("id", id);
        message.add("buffer", buffer);
        call(message);
    }
    
    public final void fwrite(String dirsymbol, Directory directory, byte type) {
        Message message = new Message("directory_write");
        message.add("symbol", dirsymbol);
        message.add("directory", directory);
        message.add("type", type);
        call(message);
    }
    
    /**
     * 
     * @param name
     * @return
     */
    public final Authorization getAuthorization(String name) {
        Message message = new Message("get_auth");
        message.add("name", name);
        return call(message);
    }
    
    /**
     * 
     * @param name
     * @return
     */
    public final UserProfile getAuthorizationProfile(String name) {
        Message message = new Message("get_auth_profile");
        message.add("name", name);
        return call(message);
    }
    
    /**
     * 
     * @param name
     * @param ns
     * @param id
     * @return
     */
    public final ComplexDocument getComplexDocument(
            String name, Object ns, Object id) {
        Message message = new Message("get_complex_document");
        message.add("name", name);
        message.add("id", id);
        message.add("ns", ns);
        return call(message);
    }
    
    /**
     * 
     * @param name
     * @return
     */
    public final ComplexModel getComplexModel(String name) {
        Message message = new Message("get_complex_model");
        message.add("name", name);
        return call(message);
    }
    
    /**
     * 
     * @return
     */
    public final ConnectionInfo[] getConnectionPoolInfo() {
        return call(new Message("connection_pool_info_get"));
    }
    
    /**
     * 
     * @param trackid
     * @return
     */
    public final String getContextId(String trackid) {
    	Message message = new Message("context_id_get");
    	message.add("track_id", trackid);
    	return call(message);
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
     * Obtem instância do modelo de documento informado.
     * @param nome do modelo
     * @return modelo de documento
     */
    public final DocumentModel getModel(String name) {
        Message message = new Message("get_document_model");
        message.add("name", name);
        return call(message);
    }
    
    /**
     * Obtem próximo número do range informado.
     * @param nome do range
     * @return número
     */
    public final long getNextNumber(String range) {
        return getNSNextNumber(range, null, null);
    }
    
    /**
     * 
     * @param range
     * @param serie
     * @return
     */
    public final long getNextNumber(String range, String serie) {
        return getNSNextNumber(range, null, serie);
    }
    
    /**
     * 
     * @param range
     * @param ns
     * @return
     */
    public final long getNSNextNumber(String range, Object ns) {
        return getNSNextNumber(range, ns, null);
    }
    
    /**
     * 
     * @param range
     * @param ns
     * @param serie
     * @return
     */
    public final long getNSNextNumber(String range, Object ns, String serie) {
        Message message = new Message("get_next_number");
        message.add("range", range);
        message.add("ns", ns);
        message.add("serie", serie);
        return call(message);
    }
    
    /**
     * Retorna entrada de dados especificada por modelo e chave.
     * @param modelname nome do modelo
     * @param key identificador (chave)
     * @return objeto encontrado ou null, se não encontrado.
     */
    public final ExtendedObject getObject(String modelname, Object key) {
        return getObject(modelname, null, key);
    }
    
    /**
     * Retorna entrada de dados especificada por modelo e chave.
     * @param modelname nome do modelo
     * @param ns namespace
     * @param key identificador (chave)
     * @return objeto encontrado ou null, se não encontrado.
     */
    public final ExtendedObject getObject(
            String modelname, Object ns, Object key) {
        Message message = new Message("get_object");
        message.add("modelname", modelname);
        message.add("ns", ns);
        message.add("key", key);
        return call(message);
    }
    
    public final Map<String, String> getText(String textnm, String id) {
        Message message = new Message("text_load");
        message.add("textname", textnm);
        message.add("id", id);
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
     * @param username
     * @return
     */
    public final Set<String> getUserProfiles(String username) {
        Message message = new Message("get_user_profiles");
        message.add("username", username);
        return call(message);
    }
    
    /**
     * 
     * @param username
     * @param secret
     * @param locale
     * @return
     */
    public final boolean login(String username, String secret, String locale) {
    	Message message = new Message("login");
    	message.add("user", username);
    	message.add("secret", secret);
    	message.add("locale", locale);
    	return call(message);
    }
    
    public final void mkdir(String... args) {
        Message message = new Message("mkdir");
        message.add("args", args);
        call(message);
    }
    
    /**
     * Atualiza entrada especificada pelo objeto extendido.
     * @param object objeto a ser atualizado.
     * @return 1, se a entrada foi atualizada com sucesso.
     */
    public final int modify(ExtendedObject object) {
        Message message = new Message("modify");
        message.add("object", object);
        return call(message);
    }
    
    /**
     * 
     * @return
     */
    public final void newContext() {
    	call(new Message("context_new"));
    }
    
    /**
     * 
     * @param view
     * @return
     */
    public final ViewExport processInput(ViewExport view) {
    	Message message = new Message("input_process");
    	message.add("view", view);
    	return call(message);
    }
    
    /**
     * 
     * @param view
     * @return
     */
    public final byte[] processOutput(ViewExport view) {
    	Message message = new Message("output_process");
    	
    	message.add("view", view);
    	return call(message);
    }
    
    /**
     * 
     * @param name
     * @return
     */
    public final int removeAuthorization(String name) {
        Message message = new Message("remove_auth");
        message.add("name", name);
        return call(message);
    }
    
    /**
     * Remove perfil de autorizações
     * @param name nome do perfil
     * @return 1, se removido com sucesso.
     */
    public final int removeAuthorizationProfile(String name) {
        Message message = new Message("remove_auth_profile");
        message.add("name", name);
        return call(message);
    }
    
    public final void removeText(String textobj, String page) {
        Message message = new Message("remove_text");
        message.add("textobj", textobj);
        message.add("id", page);
        call(message);
    }
    
    public final boolean rmdir(String... args) {
        return fdelete(true, args);
    }
    
    /**
     * 
     */
    public final void rollback() {
        call(new Message("rollback"));
    }
    
    /**
     * 
     * @param authorization
     */
    public final void save(Authorization authorization) {
        Message message = new Message("save_auth");
        message.add("authorization", authorization);
        call(message);
    }
    
    /**
     * 
     * @param profile
     */
    public final void save(UserProfile profile) {
        Message message = new Message("save_auth_profile");
        message.add("profile", profile);
        call(message);
    }
    
    /**
     * Insere entrada em tabela, especificado pelo objeto.
     * @param object objeto a ser inserido
     * @return 1, se o objeto foi inserido com sucesso.
     */
    public final int save(ExtendedObject object) {
        Message message = new Message("save_document");
        message.add("object", object);
        return call(message);
    }
    
    /**
     * 
     * @param objects
     * @return
     */
    public final int save(ExtendedObject[] objects) {
        Message message = new Message("save_documents");
        message.add("objects", objects);
        return call(message);
    }
    
    /**
     * 
     * @param document
     * @return retorna código do documento, ou 
     * 0, se erro na criação do documento;
     */
    public final ComplexDocument save(ComplexDocument document) {
        Message message = new Message("save_complex_document");
        message.add("document", document);
        return call(message);
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
    public final Object[] selectUpTo(
            String query, int rows, Object... criteria) {
        Message message = new Message("select");
        message.add("query", query);
        message.add("criteria", criteria);
        message.add("rows", rows);
        return call(message);
    }
    
    /**
     * Seleciona entradas de tabela a partir de query.
     * 
     * @param query Query SQL
     * @return entradas encontradas.
     */
    public final ExtendedObject[] select(Query query) {
        Message message = new Message("select_document");
        message.add("query", query);
        return call(message);
    }
    
    /**
     * 
     * @param query
     * @return
     */
    public final Map<Object, ExtendedObject> selectToMap(Query query) {
        Message message = new Message("select_to_map");
        message.add("query", query);
        return call(message);
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
    
    public final void updateText(ToolData tooldata, String textnm) {
        Message message = new Message("tooldata_text_update");
        message.add("textname", textnm);
        message.add("editor", tooldata);
        call(message);
    }
    
    public final void updateText(String textobj, String page, String text) {
        Message message = new Message("text_update");
        message.add("textobj", textobj);
        message.add("id", page);
        message.add("text", text);
        call(message);
    }
    
}
