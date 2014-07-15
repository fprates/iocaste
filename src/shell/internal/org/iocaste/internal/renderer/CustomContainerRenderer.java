package org.iocaste.internal.renderer;

import java.util.ArrayList;
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
        List<XMLElement> xmlcontent;
        Input input;
        CustomContainer returned;
        GenericService service = new GenericService(
                config.function, container.getRenderURL());
        Message message = new Message("render");

        message.add("container", container);
        returned = service.invoke(message);
        for (String name : returned.properties().keySet())
            container.set(name, returned.get(name));
        
        container.clear();
        input = new Input();
        input.container = container;
        input.function = config.function;
        input.view = config.getView();
        input.enablecustom = true;
        
        for (Element element : returned.getView().getElements().values()) {
            if (input.view.getElement(element.getName()) != null)
                continue;
            element.setView(input.view);
            input.view.index(element);
        }
        
        for (Element element : returned.getElements()) {
            element.setView(input.view);
            container.add(element);
            input.element = element;
            input.register();
        }

        config.customs.add(container);
        xmlcontent = new ArrayList<>();
        for (Element element : container.getElements())
            renderElement(xmlcontent, element, config);
        return xmlcontent;
    }
}
