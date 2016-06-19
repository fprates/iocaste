package org.iocaste.sysconfig;

import org.iocaste.shell.common.MessageSource;

public class Messages {

    public Messages(MessageSource messages) {
        messages.instance("pt_BR");
        messages.put("iocaste-sysconfig", "Configuração do sistema");
        messages.put("NAME", "Nome");
        messages.put("save", "Salvar");
        messages.put("shell_tab", "Shell");
        messages.put("VALUE", "Valor");
    }
}
