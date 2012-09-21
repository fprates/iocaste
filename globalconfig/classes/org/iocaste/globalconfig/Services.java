package org.iocaste.globalconfig;

import org.iocaste.protocol.AbstractFunction;
import org.iocaste.protocol.Message;

/**
 * 
 * @author francisco.prates
 *
 */
public class Services extends AbstractFunction {

    public Services() {
        export("define", "define");
        export("get", "get");
        export("remove", "remove");
        export("set", "set");
    }
    
    public final void define(Message message) {
        
    }
    
    public final Object get(Message message) {
        return null;
    }
    
    public final void remove(Message message) {
        
    }
    
    public final void set(Message message) {
        
    }
}
