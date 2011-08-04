package org.iocaste.shell.common;

public class Menu extends AbstractContainer {
    private static final long serialVersionUID = -7154471704088517956L;
    private String action;
    private Parameter parameter;
    
    public Menu(Container container, String action) {
        super(container, Const.MENU);
        this.action = action;
        parameter = new Parameter(this, action);
        parameter.setName("function");
    }
    
    public final String getAction() {
        return action;
    }
    
    public final Parameter getParameter() {
        return parameter;
    }

    public final void setParameter(Parameter parameter) {
        this.parameter = parameter;
    }
}
