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
        messages.put("FIRSTNAME", "Nome");
        messages.put("GROUP", "Grupo de tarefas");
        messages.put("INIT", "Senha inicial?");
        messages.put("PROFILE", "Perfil");
        messages.put("SECRET", "Senha");
        messages.put("SU01", "Gerenciamento de usuários");
        messages.put("SURNAME", "Sobrenome");
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
