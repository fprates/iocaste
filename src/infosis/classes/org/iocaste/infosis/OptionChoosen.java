package org.iocaste.infosis;

import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.iocaste.appbuilder.common.AbstractActionHandler;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.protocol.Iocaste;

public class OptionChoosen extends AbstractActionHandler {

    @Override
    protected void execute(PageBuilderContext context) throws Exception {
        ExtendedObject object;
        DocumentModel model;
        Properties properties = null;
        Set<String> sessions = null;
        Iocaste iocaste = null;
        Map<String, Object> sessiondata;
        String choice = dbactionget("menu", "items");
        Context extcontext = getExtendedContext();
        
        extcontext.report.clear();
        switch (choice) {
        case Main.JVPR:
            properties = System.getProperties();
            break;
        case Main.SINF:
            properties = new Iocaste(context.function).getSystemInfo();
            break;
        case Main.ULST:
            iocaste = new Iocaste(context.function);
            sessions = iocaste.getSessions();
        }
        
        if (properties != null) {
            model = new Documents(context.function).getModel("INFOSYS_REPORT");
            for (Object key : properties.keySet()) {
                object = new ExtendedObject(model);
                object.set("NAME", key);
                object.set("VALUE", properties.get(key));
                extcontext.report.add(object);
            }
        }
        
        if (sessions != null) {
            model = new Documents(context.function).getModel("INFOSYS_SESSION");
            for (String session : sessions) {
                sessiondata = iocaste.getSessionInfo(session);
                object = new ExtendedObject(model);
                object.set("USERNAME", sessiondata.get("username"));
                object.set("TERMINAL", sessiondata.get("terminal"));
                object.set("STARTED", sessiondata.get("connection.time"));
                extcontext.report.add(object);
            }
        }
        
        redirect(choice);
    }

}
