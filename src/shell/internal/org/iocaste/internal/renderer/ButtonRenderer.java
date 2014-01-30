package org.iocaste.internal.renderer;

import org.iocaste.protocol.utils.XMLElement;
import org.iocaste.shell.common.Button;

public class ButtonRenderer extends Renderer {
    
    /**
     * 
     * @param button
     * @return
     */
    public static final XMLElement render(Button button, Config config) {
        StringBuilder onclick;
        String text_ = button.getText();
        String name = button.getName();
        String htmlname = button.getHtmlName();
        XMLElement buttontag= new XMLElement("input");
        
        if (text_ == null)
            text_ = name;
        
        onclick = new StringBuilder("formSubmit('").
                append(config.getCurrentForm()).
                append("', '").append(config.getCurrentAction()).
                append("', '").append(htmlname).append("');");
        
        buttontag.add("type", (!button.isSubmit())? "button" : "submit");
        buttontag.add("name", htmlname);
        buttontag.add("id", htmlname);
        buttontag.add("class", button.getStyleClass());
        buttontag.add("value", config.getText(text_, name));
        buttontag.add("onClick", onclick.toString());
        if (!button.isEnabled())
            buttontag.add("disabled", "disabled");
        
        addEvents(buttontag, button);
        
        return buttontag;
    }

}
