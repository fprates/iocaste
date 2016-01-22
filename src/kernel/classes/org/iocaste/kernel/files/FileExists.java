package org.iocaste.kernel.files;

import java.io.File;

import org.iocaste.protocol.AbstractHandler;
import org.iocaste.protocol.Message;

public class FileExists extends AbstractHandler {

    @Override
    public Object run(Message message) throws Exception {
        String[] args = message.get("args");
        String path = FileServices.getPath(args);
        File file = new File(path);
        return file.exists();
    }
     
}
