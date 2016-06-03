package org.iocaste.texteditor;

import org.iocaste.protocol.AbstractHandler;
import org.iocaste.protocol.Iocaste;
import org.iocaste.protocol.Message;

public class TextUnregister extends AbstractHandler {

    @Override
    public Object run(Message message) throws Exception {
        String name = message.get("name");
        Iocaste iocaste = new Iocaste(getFunction());
        iocaste.rmdir("texteditor", name);
        return null;
    }

}
