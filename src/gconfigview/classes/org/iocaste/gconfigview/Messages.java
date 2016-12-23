package org.iocaste.gconfigview;

import org.iocaste.shell.common.MessageSource;

public class Messages extends MessageSource {

    @Override
    public final void entries() {
        instance("pt_BR");
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
