package org.iocaste.shell.common;

import java.util.HashMap;
import java.util.Map;

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
public class Link extends AbstractControlComponent {
    private static final long serialVersionUID = 667738108271176995L;
    private boolean absolute;
    private Map<String, LinkEntry> values;
    private String image;
    
    public Link(View view, String name, String action) {
        super(view, Const.LINK, name);
        init(name, action);
    }
    
    public Link(Container container, String name, String action) {
        super(container, Const.LINK, name);
        init(name, action);
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
    
    /**
     * Retorna endereço da imagem.
     * @return endereço.
     */
    public final String getImage() {
        return image;
    }
    
    /**
     * Retorna parâmetros do link.
     * @return parâmetros.
     */
    public final Map<String, LinkEntry> getParametersMap() {
        return values;
    }
    
    private final void init(String name, String action) {
        setText(name);
        setAction(action);
        setStyleClass("link");
        absolute = false;
        values = new HashMap<>();
    }
    
    /**
     * Indica se link é absoluto.
     * @return true, se link é absoluto.
     */
    public final boolean isAbsolute() {
        return absolute;
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
}