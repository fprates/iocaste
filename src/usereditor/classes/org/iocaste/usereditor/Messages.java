package org.iocaste.usereditor;

import org.iocaste.shell.common.MessageSource;

public class Messages {

    public Messages(MessageSource messages) {
        messages.instance("pt_BR");
        messages.put("create", "Criar");
        messages.put("delete", "Remover");
        messages.put("display", "Exibir");
        messages.put("extrastab", "Extras");
        messages.put("idtab", "Identificação");
        messages.put("invalid.user", "Usuário inválido.");
        messages.put("profiletab", "Perfis");
        messages.put("save", "Salvar");
        messages.put("secret.confirm", "Confirmação");
        messages.put("TASK", "Tarefa inicial");
        messages.put("taskstab", "Tarefas");
        messages.put("undefined.password", "Senha não especificada.");
        messages.put("update", "Atualizar");
        messages.put("user.already.exists", "Usuário já existe.");
        messages.put("user.dropped", "Usuário removido com sucesso.");
        messages.put("user.saved.successfully", "Usuário gravado com sucesso.");
        messages.put("user-selection", "Seleção de usuário");
        messages.put("usereditor-create", "Criar usuário");
        messages.put("usereditor-display", "Exibir usuário");
        messages.put("usereditor-update", "Atualizar usuário");
    }
}
