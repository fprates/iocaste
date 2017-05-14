package org.iocaste.runtime.common.protocol;

import java.util.Map;

import org.iocaste.protocol.Message;
import org.iocaste.protocol.Service;
import org.iocaste.protocol.StandardService;

public abstract class AbstractRuntimeInterface {
    private Service service;
    protected ServiceInterfaceData data;
    
    /**
     * 
     * @param message
     * @return
     */
    @SuppressWarnings("unchecked")
    protected final <T> T call(Message message) {
    	message.setSessionid(data.sessionid);
        return (T)service.call(message);
    }
    
    /**
     * 
     * @param function
     * @param params
     * @return
     */
    public final Object callAuthorized(String function,
            Map<String, Object> params) {
        Message message = new Message(function);
        for (String key : params.keySet())
            message.add(key, params.get(key));
        
        return call(message);
    }
    
    /**
     * 
     * @param function
     * @param path
     */
    protected final void initService(ServiceInterfaceData data) {
        ServiceUrl serviceurl;
        
        this.data = data;
        if (data.serviceurl == null) {
            serviceurl = (ServiceUrl)getClass().getAnnotation(ServiceUrl.class);
            data.serviceurl = serviceurl.value();
        }
        if (data.serviceurl == null)
            throw new RuntimeException("undefined service url");
        service = serviceInstance();
    }

	protected Service serviceInstance() {
        String url = new StringBuffer(data.servername).
        		append(data.serviceurl).toString();
        return new StandardService(null, url);
	}
}
