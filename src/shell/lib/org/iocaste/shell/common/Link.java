package org.iocaste.shell.common;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.iocaste.documents.common.DataType;

/**
 * Implementação de link html.
 * 
 * O nome do componente será exibido no link, a menos que
 * setText() ou setImage() estejam definidos.
 * 
 * @author francisco.prates
 *
 */
public class Link extends AbstractControlComponent implements Container {
    private static final long serialVersionUID = 667738108271176995L;
    private boolean absolute;
    private Map<String, LinkEntry> values;
    private String image, container;
    
    public Link(View view, String name, String action) {
        super(view, Const.LINK, name);
        init(null, name, action);
    }
    
    public Link(Container container, String name, String action) {
        super(container, Const.LINK, name);
        init(container, name, action);
    }

    @Override
    public void add(Element element) {
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

    @Override
    public void clear() {
        getLinkContainer().clear();
    }

    @Override
    public <T extends Element> T getElement(String name) {
        return getLinkContainer().getElement(name);
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
    
    private final void init(Container parent, String name, String action) {
        setText(name);
        setAction(action);
        setStyleClass("link");
        absolute = false;
        values = new HashMap<>();
        container = name.concat("_cnt");
        new LinkContainer(parent, container);
    }
    
    /**
     * Indica se link é absoluto.
     * @return true, se link é absoluto.
     */
    public final boolean isAbsolute() {
        return absolute;
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
        this.absolute = absolute;
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
