package org.iocaste.datadict;

import org.iocaste.appbuilder.common.AbstractInstallObject;
import org.iocaste.appbuilder.common.MessagesInstall;
import org.iocaste.appbuilder.common.StandardInstallContext;

public class TextsInstall extends AbstractInstallObject {

    @Override
    protected void execute(StandardInstallContext context) throws Exception {
        MessagesInstall messages;
        
        messages = messageInstance("pt_BR");
        messages.put("NAME", "Nome");
        messages.put("SE11", "Editor de modelos de dados");
    }

}
