package org.iocaste.infosis;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.packagetool.common.InstallData;
import org.iocaste.protocol.user.Authorization;

public class Install {

    public static final InstallData init() {
        InstallData data = new InstallData();
        Authorization authorization = new Authorization("INFOSIS.EXECUTE");
        Map<String, String> messages = new HashMap<String, String>();
        
        messages.put("jdbc_driver_version", "Versão do driver JDBC");
        messages.put("jdbc_driver_name", "Nome do driver JDBC");
        messages.put("db_product_version", "Versão do banco de dados");
        messages.put("db_product_name", "Nome do banco de dados");
        messages.put("name", "Nome");
        messages.put("value", "Valor");
        messages.put("system-info", "Informações do servidor");
        messages.put("java-properties", "Propriedades do ambiente java");
        messages.put("infosis", "Informações do sistema");
        data.setMessages("pt_BR", messages);
        
        authorization.setObject("APPLICATION");
        authorization.setAction("EXECUTE");
        authorization.add("APPNAME", "iocaste-infosis");
        data.add(authorization);
        
        data.link("INFOSIS", "iocaste-infosis");
        
        return data;
    }
}
