package org.iocaste.shell.common;

public class TextField extends AbstractInputComponent {
    private static final long serialVersionUID = 4027561075976606307L;
    private boolean password;
    
    public TextField(Container container) {
        super(container, Const.TEXT_FIELD);
        password = false;
    }
    
    /**
     * 
     * @return
     */
    public final boolean isPassword() {
        return password;
    }
    
    /**
     * 
     * @param password
     */
    public final void setPassword(boolean password) {
        this.password = password;
    }
}
