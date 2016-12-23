package org.iocaste.login;

import org.iocaste.shell.common.MessageSource;

public class Messages extends MessageSource {
    
    @Override
    public final void entries() {
        instance("pt_BR");
        put("authentic", "Autenticação");
        put("changesecret", "Alterar");
        put("CONFIRM", "Repita a senha");
        put("connect", "Conectar");
        put("invalid.login", "Usuário ou senha inválidos.");
        put("LOCALE", "Idioma");
        put("password.change", "Alteração de senha");
        put("password.mismatch", "Senhas não são iguais.");
        put("SECRET", "Senha");
        put("USERNAME", "Usuário");
    }
}
