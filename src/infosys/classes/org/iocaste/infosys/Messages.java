package org.iocaste.infosys;

import org.iocaste.shell.common.MessageSource;

public class Messages extends MessageSource {
    
    public final void entries() {
        instance("pt_BR");
        put("db_product_version", "Versão do banco de dados");
        put("db_product_name", "Nome do banco de dados");
        put("java-properties", "Propriedades do ambiente java");
        put("jdbc_driver_version", "Versão do driver JDBC");
        put("jdbc_driver_name", "Nome do driver JDBC");
        put("NAME", "Nome");
        put("STARTED", "Iniciado em");
        put("SYSINFO", "Informações do sistema");
        put("system-info", "Informações do servidor");
        put("TERMINAL", "Terminal");
        put("USERNAME", "Usuário");
        put("users-list", "Usuários conectados");
        put("usrsrfrsh", "Atualizar");
        put("VALUE", "Valor");
    }
}