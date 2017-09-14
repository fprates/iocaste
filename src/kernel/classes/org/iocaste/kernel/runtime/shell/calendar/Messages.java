package org.iocaste.kernel.runtime.shell.calendar;

import org.iocaste.shell.common.MessageSource;

public class Messages extends MessageSource {

    public final void entries() {
        instance("pt_BR");
        put("sunday", "dom");
        put("monday", "seg");
        put("tuesday", "ter");
        put("wednesday", "qua");
        put("thursday", "qui");
        put("friday", "sex");
        put("saturday", "sáb");
        put("cancel", "Cancelar");
    }
}
