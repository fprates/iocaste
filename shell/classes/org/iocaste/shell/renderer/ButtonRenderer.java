package org.iocaste.shell.renderer;

import org.iocaste.shell.XMLElement;
import org.iocaste.shell.common.Button;

public class ButtonRenderer extends Renderer {
    
    /**
     * 
     * @param button
     * @return
     */
    public static final XMLElement render(Button button, Config config) {
        String text_ = button.getText();
        String name = button.getName();
        XMLElement buttontag= new XMLElement("input");
        
        if (text_ == null)
            text_ = name;
        
        buttontag.add("type", (!button.isSubmit())? "button" : "submit");
        buttontag.add("name", name);
        buttontag.add("id", name);
        buttontag.add("class", button.getStyleClass());
        buttontag.add("value", config.getText(text_, name));
        buttontag.add("onClick", "defineAction('" +
        		config.getCurrentAction() + "', '" + name + "')");
        
        addAttributes(buttontag, button);
        
        return buttontag;
    }

}
