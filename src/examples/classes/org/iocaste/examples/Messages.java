package org.iocaste.examples;

import org.iocaste.shell.common.MessageSource;

public class Messages extends MessageSource {

    @Override
    public final void entries() {
        instance("pt_BR");
        put("EXAMPLES", "Exemplos");
        put("hello", "Olá, enfermeira!");
        put("main", "Selecione seu exemplo");
    }
}
