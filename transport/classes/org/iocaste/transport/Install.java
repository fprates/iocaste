package org.iocaste.transport;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.packagetool.common.InstallData;
import org.iocaste.protocol.user.Authorization;

public class Install {

    public static final InstallData init() {
        Map<String, String> messages;
        InstallData data = new InstallData();
        Authorization authorization = new Authorization("TRANSPORT.EXECUTE");
        
        authorization.setObject("APPLICATION");
        authorization.setAction("EXECUTE");
        authorization.add("APPNAME", "iocaste-transport");
        data.add(authorization);
        
        messages = new HashMap<String, String>();
        messages.put("object", "Objeto");
        messages.put("transport-utility", "Utilitário de transporte");
        messages.put("export", "Exportação");
        messages.put("import", "Importação");
        messages.put("add", "Adicionar");
        messages.put("generate", "Gerar");
        messages.put("upload", "Carregar");
        messages.put("pool", "Pool de ordens");
        messages.put("SE09", "Utilitário de transporte de ajustes");
        data.setMessages("pt_BR", messages);
        
        data.addTaskGroup("DEVELOP", "SE09");
        data.link("SE09", "iocaste-transport");
        
        return data;
    }
}
