package org.iocaste.internal.renderer;

import org.iocaste.internal.XMLElement;
import org.iocaste.shell.common.TextArea;

public class TextAreaRenderer extends Renderer {

    public static final XMLElement render(TextArea area, Config config) {
        XMLElement areatag = new XMLElement("textarea");
        String name = area.getHtmlName();
        String value = area.get();
        
        areatag.add("id", name);
        areatag.add("name", name);
        areatag.add("class", area.getStyleClass());
        areatag.addInner((value == null)? "" : value);
        
        return areatag;
    }
}
