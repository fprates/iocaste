package org.iocaste.datamigr;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.packagetool.common.InstallData;
import org.iocaste.protocol.user.Authorization;

public class Install {

    public static final InstallData init() {
        Map<String, String> messages;
        Authorization authorization;
        InstallData data = new InstallData();
        
        // mensagens
        messages = new HashMap<String, String>();
        messages.put("DATAMIGR", "Migração de dados");
        messages.put("iocaste-datamigr", "Ferramenta de migração de dados");
        data.setMessages("pt_BR", messages);
        
        // autorização de execução
        authorization = new Authorization("DATAMIGR.EXECUTE");
        authorization.setObject("APPLICATION");
        authorization.setAction("EXECUTE");
        authorization.add("APPNAME", "iocaste-datamigr");
        data.add(authorization);
        
        // link
        data.link("DATAMIGR", "iocaste-datamigr");
        data.addTaskGroup("DEVELOP", "DATAMIGR");
        
        return data;
    }
}
