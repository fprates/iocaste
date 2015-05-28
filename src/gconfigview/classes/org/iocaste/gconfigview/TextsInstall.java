package org.iocaste.gconfigview;

import org.iocaste.appbuilder.common.AbstractInstallObject;
import org.iocaste.appbuilder.common.MessagesInstall;
import org.iocaste.appbuilder.common.StandardInstallContext;

public class TextsInstall extends AbstractInstallObject {

    @Override
    protected void execute(StandardInstallContext context) throws Exception {
        MessagesInstall messages;
        
        messages = messageInstance("pt_BR");
        messages.put("config.display", "Exibir configurações");
        messages.put("config.edit","Editar configurações");
        messages.put("config.select","Selecionar configuração");
        messages.put("display", "Exibir");
        messages.put("edit", "Editar");
        messages.put("GCONFIGVIEW", "Configurações globais");
        messages.put("NAME", "Aplicação");
        messages.put("parameters.save.error",
                "Erro na gravação dos parâmetros");
        messages.put("save", "Gravar");
        messages.put("save.successful", "Configurações salvas com sucesso.");
    }
}
