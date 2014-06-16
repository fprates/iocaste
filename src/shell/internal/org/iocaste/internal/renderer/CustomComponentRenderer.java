package org.iocaste.internal.renderer;

import org.iocaste.protocol.GenericService;
import org.iocaste.protocol.Message;
import org.iocaste.protocol.utils.XMLElement;
import org.iocaste.shell.common.CustomComponent;

public class CustomComponentRenderer extends Renderer {

    public static XMLElement render(CustomComponent component, Config config) {
        GenericService service = new GenericService(
                config.function, component.getRenderURL());
        Message message = new Message("render");
        message.add("element", component);
        return service.invoke(message);
    }
}
