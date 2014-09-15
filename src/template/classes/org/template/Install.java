package org.template;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.appbuilder.common.AbstractInstallObject;
import org.iocaste.appbuilder.common.StandardInstallContext;

public class Install extends AbstractInstallObject {

    @Override
    protected void execute(StandardInstallContext context) throws Exception {
        Map<String, String> messages;
        
        // mensagens
        messages = new HashMap<>();
        messages.put("TESTE", "Aplicação template");
        messages.put("iocaste-template", "Módulo template");
        messages.put("server.test", "Clique no botão para testar o servidor.");
        context.getInstallData().setMessages("pt_BR", messages);
    }
}
