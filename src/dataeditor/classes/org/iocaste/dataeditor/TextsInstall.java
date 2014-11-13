package org.iocaste.dataeditor;

import org.iocaste.appbuilder.common.AbstractInstallObject;
import org.iocaste.appbuilder.common.MessagesInstall;
import org.iocaste.appbuilder.common.StandardInstallContext;

public class TextsInstall extends AbstractInstallObject {

    @Override
    protected void execute(StandardInstallContext context) throws Exception {
        MessagesInstall messages = messageInstance("pt_BR");
        
        messages.put("acceptitems", "Aceitar");
        messages.put("additems", "Adicionar");
        messages.put("dataeditor-selection", "Seleção de modelo");
        messages.put("display", "Exibir");
        messages.put("edit", "Atualizar");
        messages.put("NAME", "Modelo");
        messages.put("removeitems", "Remover");
        messages.put("save", "Salvar");
        messages.put("SM30", "Editor de entradas em modelos");
    }
}
