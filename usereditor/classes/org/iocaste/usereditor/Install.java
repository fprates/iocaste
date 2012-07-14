package org.iocaste.usereditor;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.packagetool.common.InstallData;
import org.iocaste.packagetool.common.SearchHelpData;
import org.iocaste.protocol.user.Authorization;

public class Install {

    public static final InstallData init() {
        SearchHelpData sh;
        Authorization authorization;
        Map<String, String> messages;
        InstallData data = new InstallData();
        
        authorization = new Authorization("USEREDITOR.EXECUTE");        
        authorization.setObject("APPLICATION");
        authorization.setAction("EXECUTE");
        authorization.add("APPNAME", "iocaste-usereditor");
        data.add(authorization);
        
        messages = new HashMap<String, String>();
        messages.put("addprofile", "Adicionar");
        messages.put("addtask", "Adicionar");
        messages.put("create", "Criar");
        messages.put("display", "Exibir");
        messages.put("GROUP", "Grupo de tarefas");
        messages.put("idtab", "Identificação");
        messages.put("invalid.user", "Usuário inválido.");
        messages.put("PROFILE", "Perfil");
        messages.put("profiletab", "Perfis");
        messages.put("removeprofile", "Remover");
        messages.put("removetask", "Remover");
        messages.put("save", "Salvar");
        messages.put("SECRET", "Senha");
        messages.put("secret.confirm", "Confirmação");
        messages.put("SU01", "Gerenciamento de usuários");
        messages.put("taskstab", "Tarefas");
        messages.put("update", "Atualizar");
        messages.put("user.saved.successfully", "Usuário gravado com sucesso.");
        messages.put("user.already.exists", "Usuário já existe.");
        messages.put("user-selection", "Seleção de usuário");
        messages.put("usereditor-create", "Criar usuário");
        messages.put("usereditor-display", "Exibir usuário");
        messages.put("usereditor-update", "Atualizar usuário");
        messages.put("USERNAME", "Usuário");
        data.setMessages("pt_BR", messages);
        
        data.link("SU01", "iocaste-usereditor");
        data.addTaskGroup("ADMIN", "SU01");
        
        sh = new SearchHelpData();
        sh.setModel("LOGIN");
        sh.setExport("USERNAME");
        sh.setName("SH_USER");
        sh.add("USERNAME");
        data.add(sh);
        
        sh = new SearchHelpData();
        sh.setModel("USER_PROFILE");
        sh.setExport("NAME");
        sh.setName("SH_USER_PROFILE");
        sh.add("NAME");
        data.add(sh);
        
        sh = new SearchHelpData();
        sh.setModel("TASKS_GROUPS");
        sh.setExport("NAME");
        sh.setName("SH_TASKS_GROUPS");
        sh.add("NAME");
        data.add(sh);
        
        return data;
    }
}
