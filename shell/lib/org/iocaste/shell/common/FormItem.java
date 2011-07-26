package org.iocaste.shell.common;

public class FormItem extends AbstractComponent {
    private static final long serialVersionUID = 3376883855229003535L;
    private Text text;
    private Component component;
    
    public FormItem(Form form, String name, Const type) {
        super(form, Const.FORM_ITEM);
        
        text = new Text(null);
        text.setName(name);
        text.setText(name);
        
        switch (type) {
        case TEXT_FIELD:
        case PASSWORD:
            component = new TextField(null);
            component.setName(name);
            
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
    
    public final Text getText() {
        return text;
    }
}
