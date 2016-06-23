package org.iocaste.packagetool;

import org.iocaste.shell.common.MessageSource;

public class Messages {

    public Messages(MessageSource messages) {
        messages.instance("pt_BR");
        messages.put("erdetail", "Detalhes");
        messages.put("erinstalled", "Não instaláveis");
        messages.put("EXCEPTION", "Erro");
        messages.put("indetail", "Detalhes");
        messages.put("install", "Instalar");
        messages.put("installed", "Instalados");
        messages.put("iocaste-packagetool", "Gerenciador de pacotes");
        messages.put("NAME", "Pacote");
        messages.put("remove", "Remover");
        messages.put("undetail", "Detalhes");
        messages.put("uninstalled", "Não instalados");
        messages.put("update", "Atualizar");
    }
}
