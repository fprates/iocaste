package org.iocaste.examples;

import org.iocaste.shell.common.MessageSource;

public class Messages extends MessageSource {

    @Override
    public final void entries() {
        instance("pt_BR");
        put("ADJECTIVE", "Adjetivo");
        put("AGE", "Idade");
        put("dfuse_output_fail", "");
        put("dfuse_output_ok",
                "Tenho um(a) %s para %s, a pessoa mais %s do mundo!");
        put("EXAMPLES", "Exemplos");
        put("GIFT", "Presente");
        put("header_tab", "Cabeçalho");
        put("hello", "Olá, enfermeira!");
        put("items_tab", "Ítens");
        put("main", "Selecione seu exemplo");
        put("MARRIED_ON", "Casado em");
        put("NAME", "Nome");
        put("update", "Atualizar");
    }
}
