package org.iocaste.texteditor;

import org.iocaste.protocol.AbstractHandler;
import org.iocaste.protocol.Iocaste;
import org.iocaste.protocol.Message;

public class TextUpdate extends AbstractHandler {

    @Override
    public Object run(Message message) throws Exception {
        String textobj = message.get("textobj");
        String id = message.get("id");
        String text = message.get("text");
        
        Services.update(new Iocaste(getFunction()), textobj, id, text);
        return null;
    }

}
