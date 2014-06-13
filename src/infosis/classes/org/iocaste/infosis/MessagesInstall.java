package org.iocaste.infosis;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.appbuilder.common.AbstractInstallObject;
import org.iocaste.appbuilder.common.StandardInstallContext;

public class MessagesInstall extends AbstractInstallObject {

    @Override
    protected void execute(StandardInstallContext context) {
        Map<String, String> messages = new HashMap<>();
        
        messages.put("db_product_version", "Versão do banco de dados");
        messages.put("db_product_name", "Nome do banco de dados");
        messages.put("java-properties", "Propriedades do ambiente java");
        messages.put("jdbc_driver_version", "Versão do driver JDBC");
        messages.put("jdbc_driver_name", "Nome do driver JDBC");
        messages.put("NAME", "Nome");
        messages.put("STARTED", "Iniciado em");
        messages.put("SYSINFO", "Informações do sistema");
        messages.put("system-info", "Informações do servidor");
        messages.put("TERMINAL", "Terminal");
        messages.put("USERNAME", "Usuário");
        messages.put("users-list", "Usuários conectados");
        messages.put("usrsrfrsh", "Atualizar");
        messages.put("VALUE", "Valor");
        context.getInstallData().setMessages("pt_BR", messages);
    }

}
