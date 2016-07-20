package org.iocaste.internal.renderer;

import java.util.Map;

import org.iocaste.protocol.utils.XMLElement;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.TextArea;

public class TextAreaRenderer extends AbstractElementRenderer<TextArea> {

    public TextAreaRenderer(Map<Const, Renderer<?>> renderers) {
        super(renderers, Const.TEXT_AREA);
    }

    protected final XMLElement execute(TextArea area, Config config) {
        XMLElement areatag = new XMLElement("textarea");
        String name = area.getHtmlName();
        String value = area.get();
        
        areatag.add("id", name);
        areatag.add("name", name);
        areatag.add("class", area.getStyleClass());
        areatag.add("cols", Integer.toString(area.getWidth()));
        areatag.add("rows", Integer.toString(area.getHeight()));
        
        if (!area.isEnabled())
            areatag.add("disabled", "disabled");
        
        areatag.addInner((value == null)? "" : value);
        return areatag;
    }
}
