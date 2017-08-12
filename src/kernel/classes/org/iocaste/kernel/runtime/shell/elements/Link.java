package org.iocaste.kernel.runtime.shell.elements;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DataType;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.kernel.runtime.shell.ViewContext;
import org.iocaste.shell.common.AbstractContainer;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.Element;
import org.iocaste.shell.common.LinkEntry;

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
    private String image, container;
    
    public Link(ViewContext viewctx, String name) {
        super(viewctx, Const.LINK, name);
        Object value;
        
        setText((tooldata.text == null)?
                name : tooldata.text, tooldata.textargs);
        if (tooldata.actionname == null)
            tooldata.actionname = tooldata.name;
        values = new HashMap<>();
        container = name.concat("_cnt");
        new LinkContainer(this, container);
        if (tooldata.values != null)
            for (String key : tooldata.values.keySet()) {
                if ((value = tooldata.values.get(key)) instanceof ExtendedObject)
                    add(key, (ExtendedObject)value);
                else
                    add(key, tooldata.values.get(key).toString());
            }
    }

    @Override
    public void add(Element element) {
        if (element.getHtmlName().equals(container)) {
            getView().add((Container)element);
            return;
        }
        getLinkContainer().add(element);
    }

    public final void add(String name, String value) {
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
        DataElement element = object.getModel().getModelItem(name).
                getDataElement();
        String pname = new StringBuilder(tooldata.name).append("_").
                append(name).toString();
        add(pname, object.get(name), element.getType());
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
    
    /**
     * Retorna endereço da imagem.
     * @return endereço.
     */
    public final String getImage() {
        return image;
    }
    
    private final LinkContainer getLinkContainer() {
        return (LinkContainer)getView().getElement(container);
    }
    
    /**
     * Retorna parâmetros do link.
     * @return parâmetros.
     */
    public final Map<String, LinkEntry> getParametersMap() {
        return values;
    }
    
    /**
     * Indica se link é absoluto.
     * @return true, se link é absoluto.
     */
    public final boolean isAbsolute() {
        return tooldata.absolute;
    }

    @Override
    public boolean isMultiLine() {
        return false;
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
        this.image = image;
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
