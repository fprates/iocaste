package org.iocaste.appbuilder;

import org.iocaste.shell.common.MessageSource;

public class Messages extends MessageSource {
    
    public final void entries() {
        instance("pt_BR");
        put("basetab", "Dados base");
        put("code.exists", "Documento já existe.");
        put("create", "Criar");
        put("display", "Exibir");
        put("edit", "Editar");
        put("invalid.code", "Código de documento inválido.");
        put("record.saved", "Documento %s gravado com sucesso.");
        put("save", "Gravar");
        put("validate", "Validar");
    }
}
