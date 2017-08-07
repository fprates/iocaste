package org.iocaste.runtime.common;

import java.util.Set;

import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.protocol.Message;
import org.iocaste.protocol.database.ConnectionInfo;
import org.iocaste.protocol.user.Authorization;
import org.iocaste.protocol.user.UserProfile;
import org.iocaste.protocol.utils.ConversionResult;
import org.iocaste.protocol.utils.ConversionRules;
import org.iocaste.runtime.common.application.ViewExport;
import org.iocaste.runtime.common.protocol.AbstractRuntimeInterface;
import org.iocaste.runtime.common.protocol.ServiceInterfaceData;
import org.iocaste.runtime.common.protocol.ServiceUrl;

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
    
    public final ConversionResult conversion(String xml) {
        return conversion(xml, null);
    }
    
    public final ConversionResult conversion(String xml, ConversionRules data) {
        Message message = new Message("conversion");
        message.add("xml", xml);
        message.add("data", data);
        return call(message);
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
	
    public final ConnectionInfo[] getConnectionPoolInfo() {
        return call(new Message("connection_pool_info_get"));
    }
    
	public final String getContextId(String trackid) {
		Message message = new Message("context_id_get");
		message.add("track_id", trackid);
		return call(message);
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
	
	public final String getTrackId(String trackid) {
		Message message = new Message("track_id_get");
		message.add("track_id", trackid);
		return call(message);
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
	
	public final boolean isValidContext() {
	    return call(new Message("is_valid_context"));
	}
	
	public final boolean login(String username, String secret, String locale) {
		Message message = new Message("login");
		message.add("username", username);
		message.add("secret", secret);
		message.add("locale", locale);
		return call(message);
	}
	
	public final String newContext() {
		return data.sessionid = call(new Message("context_new"));
	}
	
	public final ViewExport processInput(ViewExport view) {
		Message message = new Message("input_process");
		message.add("view", view);
		return call(message);
	}
	
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
    
    /**
     * 
     * @param authorization
     */
    public final void saveAuthorization(Authorization authorization) {
        Message message = new Message("save_auth");
        message.add("authorization", authorization);
        call(message);
    }
    
    /**
     * 
     * @param profile
     */
    public final void saveAuthorization(UserProfile profile) {
        Message message = new Message("save_auth_profile");
        message.add("profile", profile);
        call(message);
    }

}
