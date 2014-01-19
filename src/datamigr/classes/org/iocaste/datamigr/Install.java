package org.iocaste.datamigr;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.packagetool.common.InstallData;
import org.iocaste.packagetool.common.TaskGroup;
import org.iocaste.protocol.user.Authorization;

public class Install {

    public static final InstallData init() {
        TaskGroup taskgroup;
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
        taskgroup = new TaskGroup("DEVELOP");
        taskgroup.add("DATAMIGR");
        data.add(taskgroup);
        
        return data;
    }
}
