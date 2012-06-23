package org.iocaste.internal.renderer;

import java.util.Map;

import org.iocaste.internal.XMLElement;
import org.iocaste.shell.common.Link;
import org.iocaste.shell.common.Parameter;

public class LinkRenderer extends Renderer {
    
    /**
     * 
     * @param link
     * @return
     */
    public static final XMLElement render(Link link, Config config) {
        XMLElement atag, imgtag;
        String text, href, image;
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
        
        image = link.getImage();
        if (image != null) {
            imgtag = new XMLElement("img");
            imgtag.add("src", image);
            atag.addChild(imgtag);
            
            text = link.getText();
            if (text != null)
                imgtag.add("alt", config.getText(text, text));
        } else {
            text = link.getText();
            if (text != null)
                atag.addInner(config.getText(text, text));
        }
            
        return atag;
    }

}
