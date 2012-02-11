package org.iocaste.shell.common;

public class HtmlTag extends AbstractComponent {
    private static final long serialVersionUID = 8216149297894293906L;
    private String[] lines;
    private String element;
    
    public HtmlTag(Container container, String name) {
        super(container, Const.HTML_TAG, name);
        
        element = "pre";
    }

    /**
     * 
     * @return
     */
    public final String getElement() {
        return element;
    }
    
    /**
     * 
     * @return
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
     * 
     * @param element
     */
    public final void setElement(String element) {
        this.element = element;
    }
    
    /**
     * 
     * @param lines
     */
    public final void setLines(String[] lines) {
        this.lines = lines;
    }
}
