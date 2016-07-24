package org.iocaste.shell.common;

/**
 * Implementa fieldset e legend html.
 * 
 * @author francisco.prates
 *
 */
public class Frame extends AbstractContainer {
    private static final long serialVersionUID = 819231435339310268L;
    private String text, legendstyle;
    
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
        legendstyle = "text";
    }
    
    /**
     * 
     * @return
     */
    public final String getLegendStyle() {
        return legendstyle;
    }
    
    /**
     * Retorna texto do frame.
     * @return texto
     */
    public final String getText() {
        return text;
    }
    
    /**
     * 
     * @param legendstyle
     */
    public final void setLegendStyle(String legendstyle) {
        this.legendstyle = legendstyle;
    }
    
    /**
     * Ajusta texto do frame.
     * @param text texto
     */
    public final void setText(String text) {
        this.text = text;
    }
    
    @Override
    public final void translate(MessageSource messages) {
        String message;
        
        message = messages.get((this.text != null)? this.text : getName());
        if (message != null)
            text = message;
    }
}
