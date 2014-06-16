package org.iocaste.shell;

import java.util.ArrayList;
import java.util.List;

import org.iocaste.internal.renderer.Config;
import org.iocaste.internal.renderer.Renderer;
import org.iocaste.protocol.AbstractFunction;
import org.iocaste.protocol.Message;
import org.iocaste.protocol.utils.XMLElement;
import org.iocaste.shell.common.Element;

public class RenderServices extends AbstractFunction {

    public RenderServices() {
        export("render", "render");
    }
    
    public final List<XMLElement> render(Message message) {
        Element element = message.get("element");
        Config config = new Config();
        List<XMLElement> tags = new ArrayList<>();
        
        Renderer.renderElement(tags, element, config);
        return tags;
    }
}
