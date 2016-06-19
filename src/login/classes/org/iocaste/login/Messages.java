package org.iocaste.login;

import org.iocaste.shell.common.MessageSource;

public class Messages {
    
    public Messages(MessageSource messages) {
        messages.instance("pt_BR");
        messages.put("authentic", "Autenticação");
        messages.put("changesecret", "Alterar");
        messages.put("CONFIRM", "Repita a senha");
        messages.put("connect", "Conectar");
        messages.put("invalid.login", "Usuário ou senha inválidos.");
        messages.put("LOCALE", "Idioma");
        messages.put("password.change", "Alteração de senha");
        messages.put("password.mismatch", "Senhas não são iguais.");
        messages.put("SECRET", "Senha");
        messages.put("USERNAME", "Usuário");
    }
}
