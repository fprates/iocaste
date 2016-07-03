package org.iocaste.workbench;

import org.iocaste.shell.common.MessageSource;

public class Messages {

    public Messages(MessageSource messages) {
        messages.instance("pt_BR");
        messages.put("dataelement.exists", "elemeno de dados já existe.");
        messages.put("dataelement.updated", "elemento de dados atualizado.");
        messages.put("execute", "Executar");
        messages.put("iocaste.workbench", "Workbench Iocaste");
        messages.put("iocaste-workbench", "Workbench Iocaste");
        messages.put("view.element.added", "%s adicionado.");
        messages.put("WBDSPTCHR", "Workbench");
    }
}
