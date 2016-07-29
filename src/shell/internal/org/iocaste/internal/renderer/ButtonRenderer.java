package org.iocaste.internal.renderer;

import java.util.Map;

import org.iocaste.internal.EventHandler;
import org.iocaste.protocol.utils.XMLElement;
import org.iocaste.shell.common.Button;
import org.iocaste.shell.common.Const;

public class ButtonRenderer extends AbstractElementRenderer<Button> {
    
    public ButtonRenderer(Map<Const, Renderer<?>> renderers) {
        super(renderers, Const.BUTTON);
    }
    
    @Override
    protected final XMLElement execute(Button button, Config config) {
        StringBuilder onclick;
        String text, name, htmlname;
        XMLElement buttontag;
        EventHandler handler;
        
        if (!button.isVisible())
            return null;
        
        text = button.getText();
        name = button.getName();
        htmlname = button.getHtmlName();
        buttontag = new XMLElement("button");
        
        if (button.getEvent("onclick") == null) {
            if (button.isScreenLockable())
                onclick = new StringBuilder("formSubmit('");
            else
                onclick = new StringBuilder("formSubmitNoLock('");
            
            onclick.append(config.getCurrentForm()).
                    append("', '").append(config.getCurrentAction()).
                    append("', '").append(htmlname).append("');");
            handler = config.actionInstance(button.getAction());
            handler.name = htmlname;
            handler.event = "click";
            handler.call = onclick.toString();
        }
        
        if (button.getType() == Const.BUTTON)
            buttontag.add("type", (!button.isSubmit())? "button" : "submit");
        else
            buttontag.add("type", "button");
        
        buttontag.add("name", htmlname);
        buttontag.add("id", htmlname);
        buttontag.add("class", button.getStyleClass());
        if (!button.isEnabled())
            buttontag.add("disabled", "disabled");
        
        addEvents(buttontag, button);

        buttontag.addInner((text == null)? name : text);
        return buttontag;
    }

}
