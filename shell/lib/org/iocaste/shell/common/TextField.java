package org.iocaste.shell.common;

public class TextField extends AbstractComponent {
    private boolean password;
    
    public TextField(Container container) {
        super(container, Const.TEXT_FIELD);
        password = false;
    }
    
    /**
     * 
     * @return
     */
    public final boolean getPassword() {
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
