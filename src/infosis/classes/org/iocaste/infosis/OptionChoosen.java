package org.iocaste.infosis;

import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.iocaste.appbuilder.common.AbstractActionHandler;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.protocol.Iocaste;

public class OptionChoosen extends AbstractActionHandler {
    private int choice;
    
    public OptionChoosen(int choice) {
        this.choice = choice;
    }

    @Override
    protected void execute(PageBuilderContext context) throws Exception {
        ExtendedObject object;
        Properties properties = null;
        Set<String> sessions = null;
        Iocaste iocaste = null;
        Map<String, Object> sessiondata;
        Context extcontext = getExtendedContext();
        
        extcontext.report.clear();
        extcontext.title = Main.ACTIONS[choice];
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
            extcontext.model = new Documents(context.function).
                    getModel("INFOSYS_REPORT");
            for (Object key : properties.keySet()) {
                object = new ExtendedObject(extcontext.model);
                object.set("NAME", key);
                object.set("VALUE", properties.get(key));
                extcontext.report.add(object);
            }
        }
        
        if (sessions != null) {
            extcontext.model = new Documents(context.function).
                    getModel("INFOSYS_SESSION");
            for (String session : sessions) {
                sessiondata = iocaste.getSessionInfo(session);
                object = new ExtendedObject(extcontext.model);
                object.set("USERNAME", sessiondata.get("username"));
                object.set("TERMINAL", sessiondata.get("terminal"));
                object.set("STARTED", sessiondata.get("connection.time"));
                extcontext.report.add(object);
            }
        }
        
        redirect("report");
    }

}
