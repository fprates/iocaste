package org.iocaste.usereditor;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.packagetool.common.InstallData;
import org.iocaste.protocol.user.Authorization;

public class Install {

    public static final InstallData init() {
        Authorization authorization;
        Map<String, String> messages;
        InstallData data = new InstallData();
        
        authorization = new Authorization("USEREDITOR.EXECUTE");        
        authorization.setObject("APPLICATION");
        authorization.setAction("EXECUTE");
        authorization.add("APPNAME", "iocaste-usereditor");
        data.add(authorization);
        
        messages = new HashMap<String, String>();
        messages.put("user.saved.successfully", "Usuário gravado com sucesso.");
        messages.put("user.already.exists", "Usuário já existe.");
        messages.put("invalid.user", "Usuário inválido.");
        messages.put("user-selection", "Seleção de usuário");
        messages.put("USERNAME", "Usuário");
        messages.put("SECRET", "Senha");
        messages.put("secret.confirm", "Confirmação");
        messages.put("create", "Criar");
        messages.put("display", "Exibir");
        messages.put("update", "Atualizar");
        messages.put("save", "Salvar");
        messages.put("usereditor-create", "Criar usuário");
        messages.put("usereditor-display", "Exibir usuário");
        messages.put("usereditor-update", "Atualizar usuário");
        messages.put("SU01", "Gerenciamento de usuários");
        messages.put("idtab", "Identificação");
        messages.put("profiletab", "Perfis");
        messages.put("addprofile", "Adicionar");
        messages.put("removeprofile", "Remover");
        messages.put("PROFILE", "Perfil");
        data.setMessages("pt_BR", messages);
        
        data.link("SU01", "iocaste-usereditor");
        data.addTaskGroup("ADMIN", "SU01");
        
        return data;
    }
}
