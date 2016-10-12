package org.iocaste.appbuilder.install;

import org.iocaste.appbuilder.common.AbstractInstallObject;
import org.iocaste.appbuilder.common.MessagesInstall;
import org.iocaste.appbuilder.common.StandardInstallContext;

public class TextsInstall extends AbstractInstallObject {

    @Override
    protected void execute(StandardInstallContext context) throws Exception {
        MessagesInstall messages = messageInstance("pt_BR");
        
        messages.put("basetab", "Dados base");
        messages.put("create", "Criar");
        messages.put("display", "Exibir");
        messages.put("edit", "Editar");
        messages.put("save", "Gravar");
        messages.put("validate", "Validar");
    }

}
