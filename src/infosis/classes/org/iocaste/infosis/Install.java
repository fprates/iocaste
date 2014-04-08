package org.iocaste.infosis;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.packagetool.common.InstallData;
import org.iocaste.packagetool.common.TaskGroup;
import org.iocaste.protocol.user.Authorization;

public class Install {

    public static final InstallData init() {
        TaskGroup taskgroup;
        Authorization authorization;
        InstallData data = new InstallData();
        Map<String, String> messages = new HashMap<>();

        messages.put("begin", "Iniciado em");
        messages.put("db_product_version", "Versão do banco de dados");
        messages.put("db_product_name", "Nome do banco de dados");
        messages.put("infosis", "Informações do sistema");
        messages.put("INFOSIS", "Informações do sistema");
        messages.put("java-properties", "Propriedades do ambiente java");
        messages.put("jdbc_driver_version", "Versão do driver JDBC");
        messages.put("jdbc_driver_name", "Nome do driver JDBC");
        messages.put("name", "Nome");
        messages.put("system-info", "Informações do servidor");
        messages.put("terminal", "Terminal");
        messages.put("username", "Usuário");
        messages.put("users-list", "Usuários conectados");
        messages.put("usrsrfrsh", "Atualizar");
        messages.put("value", "Valor");
        data.setMessages("pt_BR", messages);

        authorization = new Authorization("INFOSIS.EXECUTE");
        authorization.setObject("APPLICATION");
        authorization.setAction("EXECUTE");
        authorization.add("APPNAME", "iocaste-infosis");
        data.add(authorization);

        authorization = new Authorization("INFOSIS.CALL");
        authorization.setObject("LINK");
        authorization.setAction("CALL");
        authorization.add("LINK", "INFOSIS");
        data.add(authorization);
        
        data.link("INFOSIS", "iocaste-infosis");
        taskgroup = new TaskGroup("ADMIN");
        taskgroup.add("INFOSIS");
        data.add(taskgroup);
        
        return data;
    }
}
