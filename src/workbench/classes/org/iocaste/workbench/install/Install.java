package org.iocaste.workbench.install;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.packagetool.common.InstallData;
import org.iocaste.packagetool.common.TaskGroup;
import org.iocaste.protocol.user.Authorization;
import org.iocaste.protocol.user.UserProfile;

public class Install {

//    public static final InstallData execute() {
//        UserProfile profile;
//        TaskGroup taskgroup;
//        Map<String, String> messages;
//        Authorization authorization;
//        InstallData data = new InstallData();
//        
//        messages = new HashMap<>();
//        messages.put("compiler.unavailable", "Compilador indisponível.");
//        messages.put("duplicated.source.name", "Nome de fonte duplicado.");
//        messages.put("invalid.command", "Comando inválido.");
//        messages.put("invalid.package", "Pacote invalido.");
//        messages.put("invalid.package.operation",
//                "Operação inválida para pacote.");
//        messages.put("invalid.project", "Projeto não encontrado.");
//        messages.put("invalid.project.operation",
//                "Operação inválida para projeto.");
//        messages.put("project.removed", "Projeto removido.");
//        messages.put("iocaste-workbench", "Workbench");
//        messages.put("WORKBENCH", "Workbench");
//        messages.put("package.created", "Pacote criado.");
//        messages.put("package.has.no.sources", "Pacote não tem fontes.");
//        messages.put("project.compiled", "Projeto compilado.");
//        messages.put("project.created", "Projeto criado.");
//        messages.put("project.has.no.packages", "Projeto sem pacotes.");
//        messages.put("project.saved", "Projeto salvo.");
//        messages.put("run", "Executar");
//        messages.put("source.created", "Fonte criado.");
//        messages.put("source.modified", "Fonte modificado.");
//        messages.put("source.not.found", "Fonte não encontrado.");
//        messages.put("unspecified.project", "Projeto não especificado.");
//        data.setMessages("pt_BR", messages);
//        
//        authorization = new Authorization("WORKBENCH.EXECUTE");
//        authorization.setObject("APPLICATION");
//        authorization.setAction("EXECUTE");
//        authorization.add("APPNAME", "iocaste-workbench");
//        data.add(authorization);
//        
//        profile = new UserProfile("DEVELOPER");
//        profile.add(authorization);
//        data.add(profile);
//        
//        data.link("WORKBENCH", "iocaste-workbench");
//        taskgroup = new TaskGroup("DEVELOP");
//        taskgroup.add("WORKBENCH");
//        data.add(taskgroup);
//        
//        Models.install(data);
//        
//        return data;
//    }
}
