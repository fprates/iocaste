package org.iocaste.report.common;

import org.iocaste.protocol.AbstractServiceInterface;
import org.iocaste.protocol.Function;
import org.iocaste.protocol.Message;

public class Report extends AbstractServiceInterface {
    private static final String SERVER = "/iocaste-report/services.html";
    
    public Report(Function function) {
        initService(function, SERVER);
    }
    
    /**
     * 
     * @param parameters
     * @return
     */
    public final byte[] getContent(ReportParameters parameters) {
        Message message = new Message();
        
        message.setId("get_content");
        message.add("parameters", parameters);
        return call(message);
    }
}
