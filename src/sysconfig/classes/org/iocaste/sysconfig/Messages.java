package org.iocaste.sysconfig;

import org.iocaste.shell.common.MessageSource;

public class Messages extends MessageSource {

    @Override
    public final void entries() {
        instance("pt_BR");
        put("iocaste-sysconfig", "Configuração do sistema");
        put("NAME", "Nome");
        put("save", "Salvar");
        put("shell_tab", "Shell");
        put("SYSCFG", "Configuração do sistema");
        put("VALUE", "Valor");
    }
}
