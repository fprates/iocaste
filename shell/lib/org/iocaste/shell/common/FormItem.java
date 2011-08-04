package org.iocaste.shell.common;

public class FormItem extends AbstractInputComponent {
    private static final long serialVersionUID = 3376883855229003535L;
    private String simplename;
    private Const type;
    
    public FormItem(Form form, String name, Const type) {
        super(form, Const.FORM_ITEM);
        
        setName(new StringBuffer(form.getName()).
                append(".").append(name).toString());
        
        simplename = name;
        this.type = type;
        
        form.addItem(this);
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
    public final String getSimpleName() {
        return simplename;
    }
}
