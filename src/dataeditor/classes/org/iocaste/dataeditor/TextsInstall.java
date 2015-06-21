package org.iocaste.dataeditor;

import org.iocaste.appbuilder.common.AbstractInstallObject;
import org.iocaste.appbuilder.common.MessagesInstall;
import org.iocaste.appbuilder.common.StandardInstallContext;

public class TextsInstall extends AbstractInstallObject {

    @Override
    protected void execute(StandardInstallContext context) throws Exception {
        MessagesInstall messages = messageInstance("pt_BR");
        
        messages.put("acceptitems", "Aceitar");
        messages.put("add", "Adicionar");
        messages.put("add.entry", "Nova entrada");
        messages.put("additems", "Adicionar");
        messages.put("continue", "Continuar");
        messages.put("dataeditor-selection", "Seleção de modelo");
        messages.put("display", "Exibir");
        messages.put("edit", "Atualizar");
        messages.put("entries.saved", "Entradas salvas.");
        messages.put("invalid.key", "Chave inválida.");
        messages.put("NAME", "Modelo");
        messages.put("new", "Nova entrada");
        messages.put("ns.key.input", "Namespace");
        messages.put("NSKEY", "Chave do namespace");
        messages.put("removeitems", "Remover");
        messages.put("save", "Salvar");
        messages.put("select", "Selecionar");
        messages.put("select.entry", "Selecionar entrada");
        messages.put("SM30", "Editor de entradas em modelos");
    }
}
