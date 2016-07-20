package org.iocaste.internal.renderer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.iocaste.documents.common.DataElement;
import org.iocaste.protocol.utils.XMLElement;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.Element;
import org.iocaste.shell.common.InputComponent;
import org.iocaste.shell.common.Shell;

public abstract class AbstractElementRenderer<T extends Element>
        implements Renderer<T> {
    private Map<Const, Renderer<?>> renderers;
    
    public AbstractElementRenderer(
            Map<Const, Renderer<?>> renderers, Const type) {
        renderers.put(type, this);
        this.renderers = renderers;
    }
    
    /**
     * 
     * @param tag
     * @param element
     */
    protected final void addEvents(XMLElement tag, Element element) {
        for (String name : element.getEventNames())
            tag.add(name, element.getEvent(name));
    }
    
    protected abstract XMLElement execute(T element, Config config);
    
    @SuppressWarnings("unchecked")
    protected final <U extends Renderer<? extends Element>> U get(Const type) {
        return (U)renderers.get(type);
    }
    
    @Override
    public final void run(List<XMLElement> parent, Container container,
            Config config) {
        XMLElement element = run(container, config);
        if (element != null)
            parent.add(element);
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public final XMLElement run(Element element, Config config) {
        return execute((T)element, config);
    }
    
    /**
     * 
     * @param elements
     * @param config
     * @return
     */
    protected final List<XMLElement> renderElements(
            Set<Element> elements, Config config) {
        XMLElement xmlelement;
        List<XMLElement> tags = new ArrayList<>();
        
        for (Element element : elements) {
            xmlelement = get(element.getType()).run(element, config);
            if (xmlelement != null)
                tags.add(xmlelement);
        }
        
        return tags;
    }
    
    /**
     * 
     * @param input
     * @return
     */
    protected final String toString(InputComponent input) {
        DataElement element = Shell.getDataElement(input);
        Object value = input.get();
        
        if (element == null || value == null) {
            if ((value != null) && input.isBooleanComponent())
                return ((Boolean)value)? "on" : "off";
            else
                return (String)value;
        }
        
        return Shell.toString(value, element, input.getLocale(),
                input.isBooleanComponent());
    }
}
