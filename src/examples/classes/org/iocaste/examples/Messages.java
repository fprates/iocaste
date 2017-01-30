package org.iocaste.examples;

import org.iocaste.shell.common.MessageSource;

public class Messages extends MessageSource {

    @Override
    public final void entries() {
        instance("pt_BR");
        put("ADJECTIVE", "Adjetivo");
        put("dfuse_output_fail", "");
        put("dfuse_output_ok",
                "Tenho um(a) %s para %s, a pessoa mais %s do mundo!");
        put("EXAMPLES", "Exemplos");
        put("GIFT", "Presente");
        put("hello", "Olá, enfermeira!");
        put("main", "Selecione seu exemplo");
        put("NAME", "Nome");
        put("update", "Atualizar");
    }
}
