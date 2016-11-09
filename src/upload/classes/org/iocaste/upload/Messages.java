package org.iocaste.upload;

import org.iocaste.shell.common.MessageSource;

public class Messages {
    
    public Messages(MessageSource messages) {
        messages.instance("pt_BR");
        messages.put("columns", "Colunas");
        messages.put("continuesel", "Continuar");
        messages.put("FILE", "Arquivo");
        messages.put("IGNORE_FIRST_LINES", "Primeiras linhas ignoradas");
        messages.put("iocaste-upload", "Utilitário de carga");
        messages.put("LAYOUT", "Layout");
        messages.put("NSKEY", "Namespace");
        messages.put("TRUNCATE_CHAR", "Truncar caracteres excedentes");
        messages.put("upload", "Carregar");
    }
}
