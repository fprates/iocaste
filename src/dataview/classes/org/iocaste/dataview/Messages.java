package org.iocaste.dataview;

import org.iocaste.shell.common.MessageSource;

public class Messages extends MessageSource {

    @Override
    public void entries() {
        instance("pt_BR");
        put("continuesel", "Continuar");
        put("dataview-selection", "Seleção de modelo");
        put("invalid.model", "Modelo inválido.");
        put("is.reference.model", "Modelo apenas para referência.");
        put("NAME", "Modelo");
        put("ns.key.input", "Visão de entradas em modelos: namespace");
        put("NSKEY", "Chave do namespace");
        put("SE16", "Visão de entradas em modelos");
        put("select", "Selecionar");
    }
}
