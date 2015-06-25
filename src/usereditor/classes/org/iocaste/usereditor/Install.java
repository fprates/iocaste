package org.iocaste.usereditor;

import org.iocaste.appbuilder.common.AbstractInstallObject;
import org.iocaste.appbuilder.common.MessagesInstall;
import org.iocaste.appbuilder.common.StandardInstallContext;
import org.iocaste.packagetool.common.SearchHelpData;

public class Install extends AbstractInstallObject {

    @Override
    protected void execute(StandardInstallContext context) throws Exception {
        SearchHelpData sh;
        MessagesInstall messages;
        
        messages = messageInstance("pt_BR");
        messages.put("acceptprofiles", "Aceitar");
        messages.put("accepttasks", "Aceitar");
        messages.put("addprofiles", "Adicionar");
        messages.put("addtasks", "Adicionar");
        messages.put("create", "Criar");
        messages.put("delete", "Remover");
        messages.put("display", "Exibir");
        messages.put("FIRSTNAME", "Nome");
        messages.put("GROUP", "Grupo de tarefas");
        messages.put("idtab", "Identificação");
        messages.put("invalid.user", "Usuário inválido.");
        messages.put("PROFILE", "Perfil");
        messages.put("profiletab", "Perfis");
        messages.put("removeprofiles", "Remover");
        messages.put("removetasks", "Remover");
        messages.put("save", "Salvar");
        messages.put("SECRET", "Senha");
        messages.put("secret.confirm", "Confirmação");
        messages.put("SU01", "Gerenciamento de usuários");
        messages.put("SURNAME", "Sobrenome");
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
        messages.put("USERNAME", "Usuário");
        
        sh = searchHelpInstance("SH_USER", "LOGIN");
        sh.setExport("USERNAME");
        sh.add("USERNAME");
        
        sh = searchHelpInstance("SH_USER_PROFILE", "USER_PROFILE");
        sh.setExport("NAME");
        sh.add("NAME");
        
        sh = searchHelpInstance("SH_TASKS_GROUPS", "TASKS_GROUPS");
        sh.setExport("NAME");
        sh.add("NAME");
    }
}
