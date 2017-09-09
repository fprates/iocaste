package org.iocaste.kernel.runtime.shell.renderer.legacy;

import org.iocaste.kernel.runtime.shell.renderer.AbstractElementRenderer;
import org.iocaste.kernel.runtime.shell.renderer.internal.Config;
import org.iocaste.kernel.runtime.shell.renderer.internal.HtmlRenderer;
import org.iocaste.protocol.utils.XMLElement;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.InputComponent;

public class ParameterRenderer extends AbstractElementRenderer<InputComponent> {
    
    public ParameterRenderer(HtmlRenderer renderers) {
        super(renderers, Const.PARAMETER);
    }

    @Override
    protected final XMLElement execute(InputComponent input, Config config) {
        XMLElement hiddentag = new XMLElement("input");
        String value, name = input.getHtmlName();
        
        hiddentag.add("type", "hidden");
        hiddentag.add("name", name);
        hiddentag.add("id", name);
        
        value = toString(input);
        if (value != null)
            hiddentag.add("value", value);
        
        addAttributes(hiddentag, input);
        
        return hiddentag;
    }
}
