package org.iocaste.copy;

import org.iocaste.shell.common.MessageSource;

public class Messages extends MessageSource {
    
    @Override
    public final void entries() {
        instance("pt_BR");
        put("copy", "Copiar");
        put("COPY", "Copiar");
        put("DATABASE", "Banco de dados externo");
        put("dbsource", "Origem banco de dados");
        put("NAME", "Modelo");
        put("NAMESPACE", "Namespace");
        put("no.records", "Sem registros para copiar.");
        put("PORT_NAME", "Porta de comunicação");
        put("portsource", "Origem porta comunicação");
        put("sucessful.copy", "Cópia bem sucedida.");
    }
}