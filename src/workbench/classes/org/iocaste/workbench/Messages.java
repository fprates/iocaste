package org.iocaste.workbench;

import org.iocaste.shell.common.MessageSource;

public class Messages {

    public Messages(MessageSource messages) {
        messages.instance("pt_BR");
        messages.put("execute", "Executar");
        messages.put("iocaste.workbench", "Workbench Iocaste");
        messages.put("iocaste-workbench", "Workbench Iocaste");
        messages.put("WBDSPTCHR", "Workbench");
    }
}
