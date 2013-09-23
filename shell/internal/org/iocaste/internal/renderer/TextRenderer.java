package org.iocaste.internal.renderer;

import org.iocaste.protocol.utils.XMLElement;
import org.iocaste.shell.common.Text;

public class TextRenderer extends Renderer {
    
    /**
     * 
     * @param text
     * @param config
     * @return
     */
    public static final XMLElement render(Text text, Config config) {
        XMLElement ptag = new XMLElement(text.getTag());
        
        ptag.add("id", text.getHtmlName());
        ptag.add("class", text.getStyleClass());

        addEvents(ptag, text);
        
        if (text.isTranslatable())
            ptag.addInner(config.getText(text.getText(), text.getName()));
        else
            ptag.addInner(text.getText());
        
        return ptag;
    }
}
