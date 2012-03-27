package org.iocaste.shell.common;

public class Frame extends AbstractContainer {
    private static final long serialVersionUID = 819231435339310268L;
    private String text;
    
    public Frame(ViewData view, String name) {
        super(view, Const.FRAME, name);
        
        text = name;
    }
    
    public Frame(Container container, String name) {
        super(container, Const.FRAME, name);
        
        text = name;
    }
    
    /**
     * 
     * @return
     */
    public final String getText() {
        return text;
    }
    
    /**
     * 
     * @param text
     */
    public final void setText(String text) {
        this.text = text;
    }
}
