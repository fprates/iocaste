package org.iocaste.shell.common;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DataType;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.shell.common.tooldata.Context;
import org.iocaste.shell.common.tooldata.ElementViewContext;
import org.iocaste.shell.common.tooldata.ToolDataElement;
import org.iocaste.shell.common.tooldata.ViewSpecItem.TYPES;

/**
 * Implementação de link html.
 * 
 * O nome do componente será exibido no link, a menos que
 * setText() ou setImage() estejam definidos.
 * 
 * @author francisco.prates
 *
 */
public class Link extends ToolDataElement {
    private static final long serialVersionUID = 667738108271176995L;
    private Map<String, LinkEntry> values;
    private String container;

    public Link(View view, String name, String action) {
        super(new ElementViewContext(view, null, TYPES.LINK, name),
                Const.LINK, name);
        init(name);
    }
    
    public Link(Container container, String name, String action) {
        super(new ElementViewContext(null, container, TYPES.LINK, name),
                Const.LINK, name);
        init(name);
    }
    
    public Link(Context viewctx, String name) {
        super(viewctx, Const.LINK, name);
        init(name);
    }

    @Override
    public void add(Element element) {
        if (element.getHtmlName().equals(container)) {
            getView().add((Container)element);
            return;
        }
        getLinkContainer().add(element);
    }

    public final void add(String name, Object value) {
        values.put(name, new LinkEntry(value, DataType.CHAR));
    }
    
    /**
     * Adiciona parâmetro ao link.
     * @param name nome do parâmetro
     * @param value valor
     */
    public final void add(String name, Object value, int type) {
        values.put(name, new LinkEntry(value, type));
    }
    
    public final void add(String name, ExtendedObject object) {
        if (object == null) {
            add(name, (Object)null);
            return;
        }
        String pname = new StringBuilder(tooldata.name).append("_").
                append(name).toString();
        add(pname, name, object);
    }
    
    public final void add(
            String name, String field, ExtendedObject object) {
        DataElement element = object.getModel().getModelItem(field).
                getDataElement();
        add(name, object.get(field), element.getType());
    }
    
    @Override
    public void clear() {
        getLinkContainer().clear();
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends Element> T getElement(String name) {
        Element element;
        return (T)(((element = getLinkContainer()) == null)?
                super.getElement(name) : element);
    }

    @Override
    public <T extends Element> Set<T> getElements() {
        return getLinkContainer().getElements();
    }

    @Override
    public String[] getElementsNames() {
        return getLinkContainer().getElementsNames();
    }
    
    private final LinkContainer getLinkContainer() {
        return (LinkContainer)getView().getElement(container);
    }
    
    @Override
    public final Map<String, LinkEntry> getParametersMap() {
        return values;
    }

    private final void init(String name) {
        Object value;
        
        setText((tooldata.text == null)?
                name : tooldata.text, tooldata.textargs);
        if (tooldata.actionname == null)
            tooldata.actionname = tooldata.name;
        values = new HashMap<>();
        container = name.concat("_cnt");
        new LinkContainer(this, container);
        if (tooldata.values == null)
            return;
        for (String key : tooldata.values.keySet())
            if ((value = tooldata.values.get(key)) instanceof ExtendedObject)
                add(key, (ExtendedObject)value);
            else
                add(key, tooldata.values.get(key).toString());
    }
    
    @Override
    public boolean isControlComponent() {
        return true;
    }

    @Override
    public void remove(Element element) {
        getLinkContainer().remove(element);
    }
    
    /**
     * Define link como absoluto.
     * 
     * url não fará referência ao domínio do iocaste.
     * 
     * @param absolute url.
     */
    public final void setAbsolute(boolean absolute) {
        tooldata.absolute = absolute;
    }
    
    /**
     * Define endereço da imagem do link.
     * @param image endereço.
     */
    public final void setImage(String image) {
        tooldata.image = image;
    }

    @Override
    public int size() {
        return getLinkContainer().size();
    }
}

class LinkContainer extends AbstractContainer {
    private static final long serialVersionUID = 1L;

    public LinkContainer(Container container, String name) {
        super(container, Const.DUMMY, name);
    }
    
}
