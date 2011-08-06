package org.iocaste.shell.common;

public class Button extends AbstractControlComponent {
    private static final long serialVersionUID = 2295137293981769652L;
    private boolean submit;
    private String text;
    
    public Button(Container container, String name) {
        super(container, Const.BUTTON, name);
        submit = false;
    }

    public final String getText() {
        return text;
    }
    
    public final boolean isSubmit() {
        return submit;
    }
    
    public final void setSubmit(boolean submit) {
        this.submit = submit;
    }
    
    public final void setText(String text) {
        this.text = text;
    }
}