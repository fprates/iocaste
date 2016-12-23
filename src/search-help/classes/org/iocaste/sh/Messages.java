package org.iocaste.sh;

import org.iocaste.shell.common.MessageSource;

public class Messages extends MessageSource {

    @Override
    public void entries() {
        instance("pt_BR");
        put("cancel", "Cancelar");
        put("bt_criteria", "Nova seleção...");
        put("iocaste-search-help", "Ajuda de pesquisa");
        put("no.results.found", "Sem resultados para esse critério.");
    }

}
