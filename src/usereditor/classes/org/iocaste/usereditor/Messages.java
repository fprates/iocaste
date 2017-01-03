package org.iocaste.usereditor;

import org.iocaste.shell.common.MessageSource;

public class Messages extends MessageSource {

    @Override
    public void entries() {
        instance("pt_BR");
        put("create", "Criar");
        put("delete", "Remover");
        put("display", "Exibir");
        put("extrastab", "Extras");
        put("FIRSTNAME", "Nome");
        put("GROUP", "Grupo de tarefas");
        put("idtab", "Identificação");
        put("INIT", "Senha inicial?");
        put("invalid.user", "Usuário inválido.");
        put("NAME", "Nome");
        put("PROFILE", "Perfil");
        put("profiletab", "Perfis");
        put("save", "Salvar");
        put("SECRET", "Senha");
        put("secret.confirm", "Confirmação");
        put("SU01", "Gerenciamento de usuários");
        put("SURNAME", "Sobrenome");
        put("TASK", "Tarefa inicial");
        put("taskstab", "Tarefas");
        put("undefined.password", "Senha não especificada.");
        put("update", "Atualizar");
        put("user.already.exists", "Usuário já existe.");
        put("user.dropped", "Usuário removido com sucesso.");
        put("user.saved.successfully", "Usuário gravado com sucesso.");
        put("user-selection", "Seleção de usuário");
        put("usereditor-create", "Criar usuário");
        put("usereditor-display", "Exibir usuário");
        put("usereditor-update", "Atualizar usuário");
        put("USERNAME", "Usuário");
    }
}
