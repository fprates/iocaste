package org.iocaste.shell.common;

public class Text extends AbstractComponent {
    private static final long serialVersionUID = -6584462992412783994L;
    private String text;
    
    public Text(Container container) {
        super(container, Const.TEXT);
    }
    
    public final String getText() {
        return text;
    }
    
    public final void setText(String text) {
        this.text = text;
    }

}
