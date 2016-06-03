package org.iocaste.texteditor;

import org.iocaste.protocol.AbstractFunction;
import org.iocaste.protocol.Iocaste;

public class Services extends AbstractFunction {

    public Services() {
        export("load", new TextLoad());
        export("register", new TextRegister());
        export("update", new EditorUpdate());
        export("update_text", new TextUpdate());
        export("unregister", new TextUnregister());
    }
    
    public static final void update(Iocaste iocaste, String textname, String id,
            String text) throws Exception {
        String[] path = {"texteditor", textname, id};
        String fd = iocaste.file(
                (!iocaste.exists(path))? Iocaste.CREATE : Iocaste.WRITE, path);
        iocaste.write(fd, ((text == null)? "" : text).getBytes());
        iocaste.close(fd);
    }
}
