package org.iocaste.kernel;

import org.iocaste.protocol.AbstractFunction;
import org.iocaste.protocol.Message;
import org.iocaste.protocol.utils.ConversionResult;
import org.iocaste.protocol.utils.ConversionRules;

public class Services extends AbstractFunction {
    
    public Services() {
        export("conversion", "conversion");
    }
    
    public final ConversionResult conversion(Message message)
            throws Exception {
        String xml = message.getst("xml");
        ConversionRules data = message.get("data");
        
        return Convesion.execute(xml, data);
    }
}
