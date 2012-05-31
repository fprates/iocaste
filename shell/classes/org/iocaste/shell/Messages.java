package org.iocaste.shell;

import java.util.Properties;

import org.iocaste.shell.common.MessageSource;

public class Messages {

    public static final MessageSource getMessages() {
        MessageSource msgsource;
        Properties messages = new Properties();
        
        messages.put("field.is.obligatory", "Campo é obrigatório.");
        messages.put("field.type.mismatch",
                "Tipo de valor incompatível com campo.");
        messages.put("invalid.value", "Valor inválido.");
        messages.put("not.connected", "Não conectado");
        messages.put("user.not.authorized", "Usuário não autorizado.");
        messages.put("home", "Inicial");
        messages.put("back", "Voltar");
        messages.put("help", "Ajuda");
        
        msgsource = new MessageSource();
        msgsource.setMessages(messages);
        
        return msgsource;
    }
}
