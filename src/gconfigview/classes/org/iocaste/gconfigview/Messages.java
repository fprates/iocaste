package org.iocaste.gconfigview;

import org.iocaste.shell.common.messages.AbstractMessages;

public class Messages extends AbstractMessages {

    @Override
    public final void entries() {
        locale("pt_BR");
        put("config.display", "Exibir configurações");
        put("config.edit","Editar configurações");
        put("config.select","Selecionar configuração");
        put("display", "Exibir");
        put("edit", "Editar");
        put("GCONFIGVIEW", "Configurações globais");
        put("NAME", "Aplicação");
        put("parameters.save.error",
                "Erro na gravação dos parâmetros");
        put("save", "Gravar");
        put("save.successful", "Configurações salvas com sucesso.");
    }
}
