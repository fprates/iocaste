package org.iocaste.shell.renderer;

import org.iocaste.shell.XMLElement;
import org.iocaste.shell.common.Parameter;

public class ParameterRenderer extends Renderer {
    
    /**
     * 
     * @param parameter
     * @return
     */
    public static final XMLElement render(Parameter parameter) {
        XMLElement hiddentag = new XMLElement("input");
        String value, name = parameter.getHtmlName();
        
        hiddentag.add("type", "hidden");
        hiddentag.add("name", name);
        hiddentag.add("id", name);
        
        value = toString(parameter);
        if (value != null)
            hiddentag.add("value", value);
        
        addAttributes(hiddentag, parameter);
        
        return hiddentag;
    }
}
