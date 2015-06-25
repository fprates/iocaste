package org.iocaste.internal.renderer;

import org.iocaste.protocol.utils.XMLElement;
import org.iocaste.shell.common.InputComponent;

public class ParameterRenderer extends Renderer {
    
    /**
     * 
     * @param parameter
     * @return
     */
    public static final XMLElement render(InputComponent input) {
        XMLElement hiddentag = new XMLElement("input");
        String value, name = input.getHtmlName();
        
        hiddentag.add("type", "hidden");
        hiddentag.add("name", name);
        hiddentag.add("id", name);
        
        value = toString(input);
        if (value != null)
            hiddentag.add("value", value);
        
        addEvents(hiddentag, input);
        
        return hiddentag;
    }
}
