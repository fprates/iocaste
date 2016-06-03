package org.iocaste.kernel.files;

import org.iocaste.protocol.AbstractHandler;
import org.iocaste.protocol.Message;
import org.iocaste.protocol.files.FileEntry;

public class FileEntriesDelete extends AbstractHandler {

    @Override
    public Object run(Message message) throws Exception {
        FileEntry[] entries = message.get("entries");
        DeleteFile delete = getFunction().get("delete");
        
        for (FileEntry entry : entries)
            delete.run(entry.path, false);
        return null;
    }

}
