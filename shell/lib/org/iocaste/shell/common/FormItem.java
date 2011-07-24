package org.iocaste.shell.common;

public class FormItem extends AbstractComponent {
    private static final long serialVersionUID = 3376883855229003535L;
    private Const type;
    private Text text;
    private TextField textfield;
    
    public FormItem(Form form, String name) {
        super(form, Const.FORM_ITEM);
        
        text = new Text(null);
        text.setName(name);
        text.setText(name);
        
        textfield = new TextField(null);
        textfield.setName(name);
        
        form.addItem(this);
    }

    public final Const getItemType() {
        return type;
    }
    
    public final Text getText() {
        return text;
    }
    
    public final TextField getTextField() {
        return textfield;
    }
    
    public final void setItemType(Const type) {
        this.type = type;
    }
}
