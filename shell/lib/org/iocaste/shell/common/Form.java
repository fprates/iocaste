package org.iocaste.shell.common;

public class Form extends AbstractContainer {
    private static final long serialVersionUID = -4049409929220114810L;
    private String enctype;
    
    public Form(Container container, String name) {
        super(container, Const.FORM, name);
        new Parameter(this, "action");
    }

    /**
     * 
     * @return
     */
    public final String getEnctype() {
        return enctype;
    }
    
    /**
     * 
     * @param enctype
     */
    public final void setEnctype(String enctype) {
        this.enctype = enctype;
    }
}
