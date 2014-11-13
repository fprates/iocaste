package org.iocaste.dataeditor;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.appbuilder.common.AbstractInstallObject;
import org.iocaste.appbuilder.common.StandardInstallContext;
import org.iocaste.packagetool.common.InstallData;

public class MessagesInstall extends AbstractInstallObject {

    @Override
    protected void execute(StandardInstallContext context) throws Exception {
        Map<String, String> messages;
        InstallData data = context.getInstallData();
        
        messages = new HashMap<>();
        messages.put("acceptitems", "Aceitar");
        messages.put("additems", "Adicionar");
        messages.put("dataeditor-selection", "Seleção de modelo");
        messages.put("display", "Exibir");
        messages.put("NAME", "Modelo");
        messages.put("removeitems", "Remover");
        messages.put("save", "Salvar");
        messages.put("SM30", "Editor de entradas em modelos");
        messages.put("edit", "Atualizar");
        data.setMessages("pt_BR", messages);        
    }
}
