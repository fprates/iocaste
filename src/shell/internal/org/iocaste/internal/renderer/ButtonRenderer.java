package org.iocaste.internal.renderer;

import org.iocaste.protocol.utils.XMLElement;
import org.iocaste.shell.common.Button;
import org.iocaste.shell.common.Component;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.SearchHelp;

public class ButtonRenderer extends Renderer {
    
    public static final XMLElement render(SearchHelp sh, Config config) {
        return _render(sh, config);
    }
    
    public static final XMLElement render(Button button, Config config) {
        return _render(button, config);
    }
    
    /**
     * 
     * @param button
     * @return
     */
    private static final XMLElement _render(Component component, Config config)
    {
        Button button;
        StringBuilder onclick;
        String text_ = component.getText();
        String name = component.getName();
        String htmlname = component.getHtmlName();
        XMLElement buttontag= new XMLElement("input");
        
        if (text_ == null)
            text_ = name;
        
        onclick = new StringBuilder("formSubmit('").
                append(config.getCurrentForm()).
                append("', '").append(config.getCurrentAction()).
                append("', '").append(htmlname).append("');");
        
        if (component.getType() == Const.BUTTON) {
            button = (Button)component;
            buttontag.add("type", (!button.isSubmit())? "button" : "submit");
        }
        
        buttontag.add("name", htmlname);
        buttontag.add("id", htmlname);
        buttontag.add("class", component.getStyleClass());
        buttontag.add("value", config.getText(text_, name));
        buttontag.add("onClick", onclick.toString());
        if (!component.isEnabled())
            buttontag.add("disabled", "disabled");
        
        addEvents(buttontag, component);
        
        return buttontag;
    }

}
