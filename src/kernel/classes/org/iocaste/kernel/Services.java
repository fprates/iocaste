package org.iocaste.kernel;

import org.iocaste.kernel.common.AbstractFunction;
import org.iocaste.kernel.common.Message;
import org.iocaste.kernel.common.utils.ConversionResult;
import org.iocaste.kernel.common.utils.ConversionRules;

public class Services extends AbstractFunction {
    
    public Services() {
        export("conversion", "conversion");
    }
    
    public final ConversionResult conversion(Message message)
            throws Exception {
        String xml = message.getString("xml");
        ConversionRules data = message.get("data");
        
        return Convesion.execute(xml, data);
    }
}
