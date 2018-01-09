package org.iocaste.kernel.runtime.shell.renderer.internal;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.kernel.runtime.shell.ViewContext;
import org.iocaste.protocol.AbstractHandler;
import org.iocaste.shell.common.MessageSource;

public abstract class AbstractProcessHandler extends AbstractHandler {
    protected static final int EINITIAL = 1;
    protected static final int EMISMATCH = 2;
    protected static final int EINVALID_REFERENCE = 3;
    protected static final int LOW_RANGE = 3;
    protected static final int HIGH_RANGE = 4;
    public static Map<String, Map<String, String>> msgsource;
    protected static Map<Integer, String> msgconv;
    
    static {
        Map<String, String> messages;
        
        msgsource = new HashMap<>();
        msgsource.put("pt_BR", messages = new HashMap<>());
        messages.put("calendar", "Calendário");
        messages.put("field.is.obligatory", "Campo é obrigatório (%s).");
        messages.put("field.type.mismatch",
                "Tipo de valor incompatível com campo.");
        messages.put("grid.options", "Opções da grid");
        messages.put("input.options", "Opções da entrada");
        messages.put("invalid.value", "Valor inválido (%s).");
        messages.put("not.connected", "Não conectado");
        messages.put("required", "Obrigatório");
        messages.put("select", "Selecionar");
        messages.put("user.not.authorized", "Usuário não autorizado.");
        messages.put("values", "Valores possíveis");
        
        msgsource.put("en_US", messages = new HashMap<>());
        messages.put("calendar", "Calendar");
        messages.put("field.is.obligatory", "Input field is required (%s).");
        messages.put("field.type.mismatch", "Input value type mismatch.");
        messages.put("grid.options", "Grid options");
        messages.put("input.options", "Input options");
        messages.put("invalid.value", "Invalid value (%s).");
        messages.put("not.connected", "Not connected");
        messages.put("required", "Obligatory");
        messages.put("select", "Select");
        messages.put("user.not.authorized", "User not authorized.");
        messages.put("values", "Suggested values");
        
        msgconv= new HashMap<>();
        msgconv.put(EINITIAL, "field.is.obligatory");
        msgconv.put(EMISMATCH, "field.type.mismatch");
        msgconv.put(EINVALID_REFERENCE, "invalid.value");
    }
    
    protected final void moveMessages(ViewContext viewctx) {
        Map<String, String> messages;
        
        viewctx.messagesrc = new MessageSource();
        viewctx.messagesrc.instance(viewctx.locale);
        messages = msgsource.get(viewctx.locale);
        for (String key : messages.keySet())
            viewctx.messagesrc.put(key, messages.get(key));
        for (int i = 0; i < viewctx.viewexport.messages.length; i++)
            viewctx.messagesrc.put(viewctx.viewexport.messages[i][0],
                    viewctx.viewexport.messages[i][1]);
        if (viewctx.viewexport.message != null)
            viewctx.messagetext = viewctx.messagesrc.get(
                    viewctx.viewexport.message,
                    viewctx.viewexport.message);
        viewctx.messageargs = viewctx.viewexport.msgargs;
        viewctx.messagetype = viewctx.viewexport.msgtype;
    }

}
