package org.iocaste.dataview;

import org.iocaste.appbuilder.common.AbstractInstallObject;
import org.iocaste.appbuilder.common.MessagesInstall;
import org.iocaste.appbuilder.common.StandardInstallContext;

public class Install extends AbstractInstallObject {

    @Override
    protected void execute(StandardInstallContext context) throws Exception {
        MessagesInstall messages;
        
        messages = messageInstance("pt_BR");
        messages.put("continuesel", "Continuar");
        messages.put("dataview-selection", "Seleção de modelo");
        messages.put("invalid.model", "Modelo inválido.");
        messages.put("is.reference.model", "Modelo apenas para referência.");
        messages.put("NAME", "Modelo");
        messages.put("ns.key.input", "Visão de entradas em modelos: namespace");
        messages.put("NSKEY", "Chave do namespace");
        messages.put("SE16", "Visão de entradas em modelos");
        messages.put("select", "Selecionar");
        
    }
}
