package org.iocaste.shell.common;

/**
 * Elemento genérico html.
 * 
 * @author francisco.prates
 *
 */
public class HtmlTag extends AbstractComponent {
    private static final long serialVersionUID = 8216149297894293906L;
    private String[] lines;
    private String element;
    
    public HtmlTag(Container container, String name) {
        super(container, Const.HTML_TAG, name);
        
        element = "pre";
    }

    /**
     * Retorna nome do elemento html.
     * @return nome
     */
    public final String getElement() {
        return element;
    }
    
    /**
     * Retorna conteúdo para elemento html.
     * @return conteúdo
     */
    public final String[] getLines() {
        return lines;
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.Element#isControlComponent()
     */
    @Override
    public boolean isControlComponent() {
        return false;
    }

    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.Element#isDataStorable()
     */
    @Override
    public boolean isDataStorable() {
        return false;
    }

    /**
     * Define nome do elemento html.
     * @param element nome
     */
    public final void setElement(String element) {
        this.element = element;
    }
    
    /**
     * Retorna conteúdo do elemento html.
     * @param lines
     */
    public final void setLines(String[] lines) {
        this.lines = lines;
    }
}
