package org.iocaste.packagetool;

import org.iocaste.shell.common.messages.AbstractMessages;

public class Messages extends AbstractMessages {

    @Override
    public void entries() {
        locale("pt_BR");
        put("action", "Ação");
        put("ADMIN", "Administração");
        put("erdetail", "Detalhes");
        put("erinstalled", "Não instaláveis");
        put("EXCEPTION", "Erro");
        put("indetail", "Detalhes");
        put("info", "Conteúdo do pacote");
        put("install", "Instalar");
        put("installed", "Instalados");
        put("iocaste-packagetool", "Gerenciador de pacotes");
        put("name", "Nome");
        put("NAME", "Pacote");
        put("PACKAGE", "Gerenciador de pacotes");
        put("package-contents", "Conteúdo do pacote");
        put("package.installed", "Pacote instalado com sucesso.");
        put("package-manager", "Gerenciador de pacotes");
        put("package.uninstalled", "Pacote desinstalado com sucesso.");
        put("package.updated", "Pacote atualizado.");
        put("packageinstall", "Instalar");
        put("packageuninstall", "Desinstalar");
        put("remove", "Remover");
        put("some.packages.failed",
                "A instalação de algum pacote falhou");
        put("undetail", "Detalhes");
        put("uninstalled", "Não instalados");
        put("update", "Atualizar");
    }
}
