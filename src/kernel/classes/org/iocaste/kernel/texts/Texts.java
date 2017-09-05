package org.iocaste.kernel.texts;

import org.iocaste.kernel.files.FileClose;
import org.iocaste.kernel.files.FileExists;
import org.iocaste.kernel.files.FileOperations;
import org.iocaste.kernel.files.FileServices;
import org.iocaste.kernel.files.FileWrite;
import org.iocaste.protocol.AbstractFunction;
import org.iocaste.protocol.Iocaste;

public class Texts extends AbstractFunction {
    public FileServices files;
    
    public Texts() {
        export("text_load", new TextLoad());
        export("text_remove", new TextRemove());
        export("text_register", new TextRegister());
        export("text_editor_update", new EditorUpdate());
        export("text_update", new TextUpdate());
        export("text_unregister", new TextUnregister());
        export("tooldata_text_update", new ToolDataTextUpdate());
    }
    
    public final void update(String sessionid, String textname, String id,
            String text) throws Exception {
        String[] path = {"texteditor", textname, id};
        FileWrite write = files.get("write");
        FileOperations fileops = files.get("file");
        FileExists exists = files.get("file_exists");
        FileClose close = files.get("file_close");
        
        String fd = fileops.run(files, sessionid,
                (!exists.run(path))? Iocaste.CREATE : Iocaste.WRITE, path);
        write.run(files, sessionid, fd, ((text == null)? "" : text).getBytes());
        close.run(files, sessionid, fd);
    }
}
