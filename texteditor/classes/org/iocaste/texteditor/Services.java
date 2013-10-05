package org.iocaste.texteditor;

import org.iocaste.protocol.AbstractFunction;
import org.iocaste.protocol.Message;

public class Services extends AbstractFunction {

    public Services() {
        export("save", "save");
    }
    
    public final void save(Message message) {
        
    }
}
