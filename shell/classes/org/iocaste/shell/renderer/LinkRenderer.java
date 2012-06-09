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
        XMLElement atag;
        String text, href;
        StringBuilder onclick;
        Map<Parameter, Object> parameters;
        
        if (!link.isEnabled()) {
            atag = new XMLElement("span");
            atag.addInner(link.getText());
            atag.add("class", link.getStyleClass());
            
            return atag;
        }
        
        atag = new XMLElement("a");
        atag.add("class", link.getStyleClass());
        
        if (link.isAbsolute())
            href = link.getAction();
        else
            href = new StringBuilder("javascript:formSubmit('").
                    append(config.getCurrentForm()).
                    append("', '").append(config.getCurrentAction()).
                    append("', '").append(link.getAction()).
                    append("');").toString();
        
        parameters = link.getParametersMap();
        if (parameters.size() > 0) {
            onclick = new StringBuilder();
            for (Parameter parameter : parameters.keySet())
                onclick.append("setValue('").append(parameter.getHtmlName()).
                        append("', '").append(parameters.get(parameter)).
                        append("');");
            
            atag.add("onClick", onclick.toString());
        }
        atag.add("href", href);
        text = link.getText();
        atag.addInner(config.getText(text, text));
        
        return atag;
    }

}
