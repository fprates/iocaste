package org.iocaste.kernel.texts;

import org.iocaste.protocol.AbstractHandler;
import org.iocaste.protocol.Message;

public class TextUpdate extends AbstractHandler {

    @Override
    public Object run(Message message) throws Exception {
        String textobj = message.get("textobj");
        String id = message.get("id");
        String text = message.get("text");
        Texts texts = getFunction();
        
        texts.update(message.getSessionid(), textobj, id, text);
        return null;
    }

}
