package org.iocaste.shell.common;

public class TextField extends AbstractInputComponent {
    private static final long serialVersionUID = 4027561075976606307L;
    private boolean password;
    
    public TextField(Container container, String name) {
        super(container, Const.TEXT_FIELD, null, name);
        password = false;
        setLength(20);
        setStyleClass("text_field");
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
