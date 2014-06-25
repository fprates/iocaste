package org.iocaste.internal.renderer;

import java.util.List;

import org.iocaste.internal.Input;
import org.iocaste.protocol.GenericService;
import org.iocaste.protocol.Message;
import org.iocaste.protocol.utils.XMLElement;
import org.iocaste.shell.common.CustomContainer;
import org.iocaste.shell.common.Element;

public class CustomContainerRenderer extends Renderer {
    
    public static List<XMLElement> render(
            CustomContainer container, Config config) {
        Input input;
        CustomContainer returned;
        GenericService service = new GenericService(
                config.function, container.getRenderURL());
        Message message = new Message("render");

        message.add("element", container);
        returned = service.invoke(message);

        container.clear();
        input = new Input();
        input.container = container;
        input.function = config.function;
        input.view = config.getView();
        for (Element element : returned.getElements()) {
            container.add(element);
            input.element = element;
            input.register();
        }
        
        return returned.getOutput();
    }
}
