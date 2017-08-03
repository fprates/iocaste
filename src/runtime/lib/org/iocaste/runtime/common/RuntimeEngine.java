package org.iocaste.runtime.common;

import org.iocaste.documents.common.DocumentModel;
import org.iocaste.protocol.Message;
import org.iocaste.protocol.database.ConnectionInfo;
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
    
    public final ConversionResult conversion(String xml) {
        return conversion(xml, null);
    }
    
    public final ConversionResult conversion(String xml, ConversionRules data) {
        Message message = new Message("conversion");
        message.add("xml", xml);
        message.add("data", data);
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
	
	public final String getTrackId(String trackid) {
		Message message = new Message("track_id_get");
		message.add("track_id", trackid);
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
}
