package org.iocaste.kernel.texts;

import org.iocaste.protocol.AbstractHandler;
import org.iocaste.protocol.Iocaste;
import org.iocaste.protocol.Message;
import org.iocaste.protocol.files.FileEntry;

public class TextRegister extends AbstractHandler {

    @Override
    public Object run(Message message) throws Exception {
        FileEntry[] files;
        String name = message.get("name");
        Iocaste iocaste = new Iocaste(getFunction());
        
        iocaste.mkdir("texteditor", name);
        files = iocaste.getFiles("texteditor", name);
        if (files != null)
            iocaste.delete(files);
        return null;
    }

}
