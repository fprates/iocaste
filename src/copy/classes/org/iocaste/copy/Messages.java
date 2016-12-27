package org.iocaste.copy;

import org.iocaste.shell.common.MessageSource;

public class Messages extends MessageSource {
    
    @Override
    public final void entries() {
        instance("pt_BR");
        put("COPY", "Copiar");
        put("NAME", "Modelo");
        put("NAMESPACE", "Namespace");
        put("no.records", "Sem registros para copiar.");
        put("PORT_NAME", "Porta de comunicação");
        put("sucessful.copy", "Cópia bem sucedida.");
    }
}