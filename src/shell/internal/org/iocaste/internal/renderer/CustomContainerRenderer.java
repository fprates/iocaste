package org.iocaste.internal.renderer;

import java.util.ArrayList;
import java.util.List;

import org.iocaste.internal.Input;
import org.iocaste.protocol.GenericService;
import org.iocaste.protocol.Message;
import org.iocaste.protocol.utils.XMLElement;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.CustomContainer;
import org.iocaste.shell.common.Element;
import org.iocaste.shell.common.View;

public class CustomContainerRenderer extends Renderer {
    
    public static List<XMLElement> render(
            CustomContainer container, Config config) {
        List<XMLElement> xmlcontent;
        Input input;
        CustomContainer returned;
        GenericService service;
        
        if (container.isDamaged()) {
            service = new GenericService(
                    config.function, container.getRenderURL());
            Message message = new Message("render");
    
            message.add("container", container);
            returned = service.invoke(message);
            
            container.set(returned.properties());
            container.restore();
            container.clear();
            input = new Input();
            input.container = container;
            input.function = config.function;
            input.view = config.getView();
            input.enablecustom = true;
            
            for (Element element : returned.getElements()) {
                transfer(input.view, container, element);
                element.setView(input.view);
                container.add(element);
                input.element = element;
                input.register();
            }
        }
        
        config.customs.add(container.getHtmlName());
        xmlcontent = new ArrayList<>();
        for (Element element : container.getElements())
            renderElement(xmlcontent, element, config);
        return xmlcontent;
    }
    
    private static void transfer(View view, Container to, Element from) {
        Container fromc;
        
        if (!from.isContainable()) {
            from.setView(view);
            to.setView(view);
            view.index(from);
            return;
        }
        
        fromc = (Container)from;
        for (Element child : fromc.getElements())
            transfer(view, fromc, child);
        fromc.setView(view);
        view.index(from);
    }
}
