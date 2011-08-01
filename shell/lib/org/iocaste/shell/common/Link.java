package org.iocaste.shell.common;

public class Link extends AbstractControlComponent {
    private static final long serialVersionUID = 667738108271176995L;
    private String action;
    private String text;
    
    public Link(Container container, String action) {
        super(container, Const.LINK);
        this.action = action;
    }

    public final String getAction() {
        return action;
    }
    
    public final String getText() {
        return text;
    }
    
    public final void setText(String text) {
        this.text = text;
    }
}