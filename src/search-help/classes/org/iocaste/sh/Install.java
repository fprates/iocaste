package org.iocaste.sh;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.packagetool.common.InstallData;

public class Install {

    public static final InstallData init() {
        Map<String, String> messages;
        InstallData data = new InstallData();
        
        messages = new HashMap<>();
        messages.put("cancel", "Cancelar");
        messages.put("iocaste-search-help", "Ajuda de pesquisa");
        messages.put("no.results.found", "Sem resultados para esse critério.");
        data.setMessages("pt_BR", messages);
        
        return data;
    }
}
