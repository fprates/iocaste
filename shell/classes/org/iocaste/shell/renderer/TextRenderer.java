package org.iocaste.shell.renderer;

import org.iocaste.shell.XMLElement;
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
        
        ptag.addInner(config.getText(text.getText(), text.getName()));
        
        return ptag;
    }
}
