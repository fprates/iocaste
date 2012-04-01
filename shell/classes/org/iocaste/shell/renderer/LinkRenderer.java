package org.iocaste.shell.renderer;

import java.util.Map;

import org.iocaste.shell.XMLElement;
import org.iocaste.shell.common.Link;
import org.iocaste.shell.common.Parameter;

public class LinkRenderer extends Renderer {
    
    /**
     * 
     * @param link
     * @return
     */
    public static final XMLElement render(Link link, Config config) {
        Map<Parameter, Object> parameters;
        StringBuffer sb;
        XMLElement atag;
        
        if (!link.isEnabled()) {
            atag = new XMLElement("span");
            atag.addInner(link.getText());
            
            return atag;
        }
        
        atag = new XMLElement("a");
        
        if (link.isAbsolute())
            sb = new StringBuffer();
        else
            sb = new StringBuffer("index.html?pagetrack=").
                    append(config.getPageTrack()).append("&action=");
        
        sb.append(link.getHtmlName());
        
        parameters = link.getParametersMap();
        
        if (parameters.size() > 0)
            for (Parameter parameter: parameters.keySet())
                sb.append("&").append(parameter.getName()).append("=").
                        append(parameters.get(parameter));

        atag.add("href", sb.toString());
        atag.addInner(link.getText());
        
        return atag;
    }

}
