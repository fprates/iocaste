package org.iocaste.kernel.runtime.shell.renderer;

import java.util.Map;

import org.iocaste.kernel.runtime.shell.renderer.internal.ActionEventHandler;
import org.iocaste.kernel.runtime.shell.renderer.internal.Config;
import org.iocaste.kernel.runtime.shell.renderer.internal.HtmlRenderer;
import org.iocaste.protocol.utils.XMLElement;
import org.iocaste.shell.common.Button;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.Shell;

public class ButtonRenderer extends AbstractElementRenderer<Button> {
    
    public ButtonRenderer(HtmlRenderer renderer) {
        super(renderer, Const.BUTTON);
    }
    
    @Override
    protected final XMLElement execute(Button button, Config config) {
        String text, name, htmlname, action;
        XMLElement buttontag;
        ActionEventHandler handler;
        Map<String, String> events;
        
        if (!button.isVisible())
            return null;
        
        text = button.getText();
        name = button.getName();
        htmlname = button.getHtmlName();
        buttontag = new XMLElement("button");
        events = button.getEvents();
        action = button.getAction();
        if (!events.containsKey("click")) {
            handler = config.viewctx.getEventHandler(name, action, "click");
            handler.name = htmlname;
            handler.call = button.isScreenLockable()?
                Shell.formSubmit(
                        config.currentform, config.currentaction, htmlname) :
                Shell.formSubmitNoLock(
                        config.currentform, config.currentaction, htmlname);
        }
        
        for (String event : events.keySet()) {
            handler = config.viewctx.getEventHandler(name, action, event);
            handler.name = htmlname;
            handler.call = events.get(event);
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
        
        addAttributes(buttontag, button);

        buttontag.addInner((text == null)? name : text);
        return buttontag;
    }

}
