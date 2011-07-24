package org.iocaste.shell.common;

public class Form extends AbstractContainer {
    private static final long serialVersionUID = -5059126959559630847L;
    private String action;
    
    public Form() {
        super(Const.FORM);
    }
    
    public final String getAction() {
        return action;
    }
    
    public final String getSubmitText() {
        return action;
    }
    
    public final void setAction(String action) {
        this.action = action;
    }
}
