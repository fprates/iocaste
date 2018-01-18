package org.iocaste.kernel.runtime.shell.renderer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.iocaste.documents.common.DataElement;
import org.iocaste.kernel.runtime.shell.factories.SpecFactory;
import org.iocaste.kernel.runtime.shell.renderer.internal.Config;
import org.iocaste.kernel.runtime.shell.renderer.internal.HtmlRenderer;
import org.iocaste.kernel.runtime.shell.renderer.internal.Renderer;
import org.iocaste.kernel.runtime.shell.renderer.internal.Source;
import org.iocaste.kernel.runtime.shell.renderer.legacy.ParameterRenderer;
import org.iocaste.protocol.utils.XMLElement;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.Element;
import org.iocaste.shell.common.InputComponent;
import org.iocaste.shell.common.Shell;

public abstract class AbstractElementRenderer<T extends Element>
        implements Renderer<T> {
    private List<InputComponent> hidden;
    private HtmlRenderer renderer;
    private Map<Const, Source> sources;
    private Const type;
    
    public AbstractElementRenderer(HtmlRenderer renderer, Const type) {
        sources = new HashMap<>();
        this.renderer = renderer;
        this.type = type;
        hidden = new ArrayList<>();
        renderer.add(this);
    }
    
    /**
     * 
     * @param tag
     * @param element
     */
    protected final void addAttributes(XMLElement tag, Element element) {
        Map<String, String> attributes = element.getAttributes();
        for (String name : attributes.keySet())
            tag.add(name, attributes.get(name));
    }
    
    protected abstract XMLElement execute(T element, Config config)
            throws Exception;
    
    protected final <R extends Renderer<? extends Element>> R get(Const type) {
        return renderer.getRenderer(type);
    }
    
    /**
     * 
     * @return
     */
    public final List<InputComponent> getHidden() {
        return hidden;
    }
    
    protected final String getStyle(InputComponent input) {
        switch (input.getType()) {
        case DATA_ITEM:
            return type.style();
        default:
            return input.getStyleClass();
        }
    }
    
    protected final Source getSource() {
        return sources.get(Const.NONE);
    }
    
    protected final Source getSource(Const type) {
        Source source = sources.get(type);
        return (source == null)? sources.get(Const.NONE) : source;
    }
    
    @Override
    public final Const getType() {
        return type;
    }
    
    protected final void hide(InputComponent input) {
        hidden.add(input);
    }
    
    protected final void put(Source source) {
        sources.put(Const.NONE, source);
    }
    
    protected final void put(Const type, Source source) {
        sources.put(type, source);
    }
    
    /**
     * 
     * @param elements
     * @param config
     * @return
     */
    protected final List<XMLElement> renderElements(
            Set<Element> elements, Config config) throws Exception {
        XMLElement xmlelement;
        List<XMLElement> tags = new ArrayList<>();
        
        for (Element element : elements) {
            xmlelement = get(element.getType()).run(element, config);
            if (xmlelement != null)
                tags.add(xmlelement);
        }
        
        return tags;
    }

    protected final void renderHiddenInput(List<XMLElement> tags,
            InputComponent input, Config config, Renderer<?> renderer)
                    throws Exception {
        tags.add(renderer.run(input, config));
    }
    
    protected final void renderHiddenInputs(List<XMLElement> tags,
            Container container, Config config) throws Exception {
        ParameterRenderer renderer = get(Const.PARAMETER);
        for (Element element : container.getElements()) {
            if (element.isContainable()) {
                renderHiddenInputs(tags, (Container)element, config);
                continue;
            }
            
            if (element.isDataStorable())
                renderHiddenInput(tags, (InputComponent)element, config, renderer);
        }
    }
    
    protected final void renderHiddenInputs(XMLElement divtag, Config config)
            throws Exception {
        ParameterRenderer renderer = get(Const.PARAMETER);
        List<XMLElement> tags = new ArrayList<>();
        
        for (InputComponent input : hidden)
            renderHiddenInput(tags, input, config, renderer);
        divtag.addChildren(tags);
    }
    
    @Override
    public final void run(List<XMLElement> parent, Container container,
            Config config) throws Exception {
        XMLElement element = run(container, config);
        if (element != null)
            parent.add(element);
    }
    
    @Override
    @SuppressWarnings("unchecked")
    public final XMLElement run(Element element, Config config)
            throws Exception {
        SpecFactory factory;

        if (!config.viewctx.noeventhandlers && (element != null)) {
            factory = config.viewctx.factories.get(
                    config.viewctx.types.get(element.getType()));
            if (factory != null)
                factory.addEventHandler(config.viewctx, element.getHtmlName());
        }
        
        hidden.clear();
        return execute((T)element, config);
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
