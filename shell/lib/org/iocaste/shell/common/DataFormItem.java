package org.iocaste.shell.common;

public class DataFormItem extends AbstractInputComponent {
    private static final long serialVersionUID = 3376883855229003535L;
    private Const type;
    private boolean secret;
    
    public DataFormItem(DataForm form, Const type, String name) {
        super(form, Const.DATA_FORM_ITEM, name);
        
        this.type = type;
        setStyleClass("form");
    }
    
    /**
     * 
     * @return
     */
    public final Const getComponentType() {
        return type;
    }
    
    /**
     * 
     * @return
     */
    public final boolean isSecret() {
        return secret;
    }
    
    /**
     * 
     * @param secret
     */
    public final void setSecret(boolean secret) {
        this.secret = secret;
    }
}
