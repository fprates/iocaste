package org.iocaste.install;

import org.iocaste.shell.common.MessageSource;

public class Messages extends MessageSource {

    @Override
    public final void entries() {
        instance("pt_BR");
        put("chngbase", "Apenas aponta para novo banco");
        put("config", "Dados da instalação");

        put("congratulations", "Parabéns!");
        put("congratulations1",
                "A instalação do Iocaste foi concluída com sucesso.");
        put("congratulations2",
                "Você será redirecionado à tela de conexão.");
        put("congratulations3",
                "O usuário é \"ADMIN\", e a senha é \"iocaste\".");
        put("congratulations4",
                "Para prosseguir, clique em \"Continuar\".");

        put("continue", "Continuar");
        put("DBNAME", "Nome do banco de dados");
        put("dbtypes", "Selecione o tipo de banco");
        put("HOST", "Hostname");
        put("keepbase", "Utiliza o banco, recria tabelas");
        put("newbase", "Cria nova banco");
        put("OPTIONS", "Tipo de instalação");
        put("SECRET", "Senha");
        put("USERNAME", "Usuário");
        
        put("warning1",
                "Informe usuário e senha para criação do banco de dados do "
                + "sistema.");
        put("warning2", "Todos os dados anteriores serão destruídos.");
        
        put("welcome", "Bem-vindo à instalação do Iocaste");
        put("welcome1",
                "Bem-vindo ao utilitário de instalação do Iocaste.");
        put("welcome2",
                "Ao prosseguir, esse utilitário ajustará o sistema para a " +
                "configuração inicial.");
        put("welcome3",
                "Nesse processo, todos os dados serão perdidos.");
        put("welcome4", "Para continuar, clique no botão \"Continuar\"");
    }
}
