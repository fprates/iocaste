package org.iocaste.texteditor;

import org.iocaste.protocol.AbstractHandler;
import org.iocaste.protocol.Iocaste;
import org.iocaste.protocol.Message;

public class TextRemove extends AbstractHandler {

    @Override
    public Object run(Message message) throws Exception {
        String textobj = message.getst("textobj");
        String page = message.getst("id");
        Iocaste iocaste = new Iocaste(getFunction());
        
        iocaste.delete("texteditor", textobj, page);
        return null;
    }
    
}