package org.iocaste.packagetool;

import org.iocaste.appbuilder.common.AbstractInstallObject;
import org.iocaste.appbuilder.common.MessagesInstall;
import org.iocaste.appbuilder.common.StandardInstallContext;

public class TextsInstall extends AbstractInstallObject{

    @Override
    protected void execute(StandardInstallContext context) throws Exception {
        MessagesInstall messages;
        
        messages = messageInstance("pt_BR");
        messages.put("package.installed", "Pacote instalado com sucesso.");
        messages.put("package.uninstalled", "Pacote desinstalado com sucesso.");
        messages.put("package-manager", "Gerenciador de pacotes");
        messages.put("packageinstall", "Instalar");
        messages.put("packageuninstall", "Desinstalar");
        messages.put("PACKAGE", "Gerenciador de pacotes");
        messages.put("name", "Nome");
        messages.put("action", "Ação");
        messages.put("ADMIN", "Administração");
        messages.put("info", "Conteúdo do pacote");
        messages.put("package-contents", "Conteúdo do pacote");
    }

}
