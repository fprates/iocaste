package org.iocaste.shell.common;

public class CheckBox extends AbstractInputComponent {
    private static final long serialVersionUID = -7042599709545616205L;
    
    public CheckBox(Container container, String name) {
        super(container, Const.CHECKBOX, null, name);
    }
    
    /**
     * 
     * @return
     */
    public final boolean isSelected() {
        if (getValue() == null)
            return false;
        
        return (getValue().equals("on"))? true : false;
    }
}
