package org.iocaste.install;

import org.iocaste.shell.common.MessageSource;

public class Messages {

    public Messages(MessageSource source) {
        source.instance("pt_BR");
        source.put("chngbase", "Apenas aponta para novo banco");
        
        source.put("congratulations1",
                "A instalação do Iocaste foi concluída com sucesso.");
        source.put("congratulations2",
                "Você será redirecionado à tela de conexão.");
        source.put("congratulations3",
                "O usuário é \"ADMIN\", e a senha é \"iocaste\".");
        source.put("congratulations4",
                "Para prosseguir, clique em \"Continuar\".");
        
        source.put("continue", "Continuar");
        source.put("DBNAME", "Nome do banco de dados");
        source.put("dbtypes", "Selecione o tipo de banco");
        source.put("HOST", "Hostname");
        source.put("keepbase", "Utiliza o banco, recria tabelas");
        source.put("newbase", "Cria nova banco");
        source.put("OPTIONS", "Tipo de instalação");
        source.put("SECRET", "Senha");
        source.put("USERNAME", "Usuário");
        
        source.put("warning1",
                "Informe usuário e senha para criação do banco de dados do "
                + "sistema.");
        source.put("warning2", "Todos os dados anteriores serão destruídos.");
        
        source.put("welcome1",
                "Bem-vindo ao utilitário de instalação do Iocaste.");
        source.put("welcome2",
                "Ao prosseguir, esse utilitário ajustará o sistema para a " +
                "configuração inicial.");
        source.put("welcome3",
                "Nesse processo, todos os dados serão perdidos.");
        source.put("welcome4", "Para continuar, clique no botão \"Continuar\"");
    }
}
