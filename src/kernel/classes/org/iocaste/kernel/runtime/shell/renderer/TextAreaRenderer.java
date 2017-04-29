package org.iocaste.kernel.runtime.shell.renderer;

import org.iocaste.kernel.runtime.shell.renderer.internal.HtmlRenderer;
import org.iocaste.protocol.utils.XMLElement;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.TextArea;

public class TextAreaRenderer extends AbstractElementRenderer<TextArea> {

    public TextAreaRenderer(HtmlRenderer renderer) {
        super(renderer, Const.TEXT_AREA);
    }

    protected final XMLElement execute(TextArea area, Config config) {
        XMLElement areatag;
        String name, value;

        if (!area.isVisible())
            return get(Const.PARAMETER).run(area, config);

        name = area.getHtmlName();
        value = area.get();
        areatag = new XMLElement("textarea");
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
