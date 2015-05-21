package org.quantic.template;

import org.iocaste.appbuilder.common.AbstractInstallObject;
import org.iocaste.appbuilder.common.MessagesInstall;
import org.iocaste.appbuilder.common.StandardInstallContext;

public class Install extends AbstractInstallObject {

    @Override
    protected void execute(StandardInstallContext arg0) throws Exception {
        MessagesInstall messages;
        
        // mensagens
        messages = messageInstance("pt_BR");
        messages.put("TESTE", "Aplicação template");
        messages.put("iocaste-template", "Módulo template");
        messages.put("server.test", "Clique no botão para testar o servidor.");
        
    }
}
