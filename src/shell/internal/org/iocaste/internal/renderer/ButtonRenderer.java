package org.iocaste.internal.renderer;

import org.iocaste.protocol.utils.XMLElement;
import org.iocaste.shell.common.Button;
import org.iocaste.shell.common.Const;

public class ButtonRenderer extends Renderer {
    
    /**
     * 
     * @param button
     * @return
     */
    public static final XMLElement render(Button button, Config config) {
        StringBuilder onclick;
        String text = button.getText();
        String name = button.getName();
        String htmlname = button.getHtmlName();
        XMLElement buttontag= new XMLElement("input");
        
        onclick = new StringBuilder("formSubmit('").
                append(config.getCurrentForm()).
                append("', '").append(config.getCurrentAction()).
                append("', '").append(htmlname).append("');");
        
        if (button.getType() == Const.BUTTON)
            buttontag.add("type", (!button.isSubmit())? "button" : "submit");
        else
            buttontag.add("type", "button");
        
        buttontag.add("name", htmlname);
        buttontag.add("id", htmlname);
        buttontag.add("class", button.getStyleClass());
        buttontag.add("value", (text == null)? name : text);
        buttontag.add("onClick", onclick.toString());
        if (!button.isEnabled())
            buttontag.add("disabled", "disabled");
        
        addEvents(buttontag, button);
        
        return buttontag;
    }

}
