package org.iocaste.shell.common;

public class FormItem extends AbstractInputComponent {
    private static final long serialVersionUID = 3376883855229003535L;
    private Text text;
    private Component component;
    
    public FormItem(Form form, String name, Const type) {
        super(form, Const.FORM_ITEM);
        String inputname = new StringBuffer(form.getName()).
                append(".").append(name).toString();
        
        setName(inputname);
        
        text = new Text(null);
        text.setName(inputname);
        
        switch (type) {
        case TEXT_FIELD:
        case PASSWORD:
            component = new TextField(null);
            component.setName(inputname);
            
            if (type == Const.TEXT_FIELD)
                ((TextField)component).setPassword(false);
            else
                ((TextField)component).setPassword(true);
            
            break;
        }
        
        form.addItem(this);
    }
    
    /**
     * 
     * @return
     */
    public final Component getComponent() {
        return component;
    }
    
    /**
     * 
     * @return
     */
    public final Text getText() {
        return text;
    }
}
