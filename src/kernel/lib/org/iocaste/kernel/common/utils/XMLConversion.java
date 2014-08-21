package org.iocaste.kernel.common.utils;

import org.iocaste.protocol.AbstractServiceInterface;
import org.iocaste.protocol.Function;
import org.iocaste.protocol.Iocaste;
import org.iocaste.protocol.Message;
import org.iocaste.protocol.utils.ConversionResult;
import org.iocaste.protocol.utils.ConversionRules;

public class XMLConversion extends AbstractServiceInterface{
    
    public XMLConversion(Function function) {
        initService(function, Iocaste.SERVERNAME);
    }
    
    public final ConversionResult conversion(String xml) {
        return conversion(xml, null);
    }
    
    public final ConversionResult conversion(String xml, ConversionRules data) {
        Message message = new Message("conversion");
        message.add("xml", xml);
        message.add("data", data);
        return call(message);
    }

}
