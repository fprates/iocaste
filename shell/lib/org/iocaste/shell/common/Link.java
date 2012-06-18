package org.iocaste.shell.common;

import java.util.HashMap;
import java.util.Map;

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
    private Map<Parameter, Object> values;
    private String image;
    
    public Link(Container container, String name, String action) {
        super(container, Const.LINK, name);
        setText(action);
        setAction(action);
        setStyleClass("link");
        absolute = false;
        values = new HashMap<Parameter, Object>();
    }

    /**
     * Adiciona parâmetro ao link.
     * @param parameter nome do parâmetros
     * @param value valor
     */
    public final void add(Parameter parameter, Object value) {
        values.put(parameter, value);
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
    public final Map<Parameter, Object> getParametersMap() {
        return values;
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
    
    /**
     * Ajusta valor dos parâmetros.
     * @param name nome do parâmetro
     * @param value valor
     */
    public final void setValue(String name, Object value) {
        for (Parameter parameter : values.keySet()) {
            if (!parameter.getName().equals(name))
                continue;
            
            values.remove(parameter);
            values.put(parameter, value);
            break;
        }
    }
}