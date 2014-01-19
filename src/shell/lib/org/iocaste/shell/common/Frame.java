package org.iocaste.shell.common;

/**
 * Implementa fieldset e legend html.
 * 
 * @author francisco.prates
 *
 */
public class Frame extends AbstractContainer {
    private static final long serialVersionUID = 819231435339310268L;
    private String text;
    
    public Frame(View view, String name) {
        super(view, Const.FRAME, name);
        init(name);
    }
    
    public Frame(Container container, String name) {
        super(container, Const.FRAME, name);
        init(name);
    }
    
    private final void init(String name) {
        text = name;
        setStyleClass("frame");
    }
    
    /**
     * Retorna texto do frame.
     * @return texto
     */
    public final String getText() {
        return text;
    }
    
    /**
     * Ajusta texto do frame.
     * @param text texto
     */
    public final void setText(String text) {
        this.text = text;
    }
}
