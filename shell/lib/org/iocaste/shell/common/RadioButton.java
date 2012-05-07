package org.iocaste.shell.common;

public class RadioButton extends AbstractInputComponent {
    private static final long serialVersionUID = 4032308949086603543L;
    private RadioGroup group;
    private int index;
    
    public RadioButton(Container container, String name, RadioGroup group) {
        super(container, Const.RADIO_BUTTON, null, name);
        
        this.group = group;
        index = group.add(this);
        
        setHtmlName(group.getName());
        setSelected(false);
    }
    
    /**
     * 
     * @return
     */
    public final RadioGroup getGroup() {
        return group;
    }
    
    /**
     * 
     * @return
     */
    public final RadioButton getSelectedComponent() {
        for (RadioButton rb : group.getComponents())
            if (rb.isSelected())
                return rb;
        
        return null;
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.AbstractInputComponent#getStackComponents()
     */
    @Override
    public final InputComponent[] getStackComponents() {
        return group.getComponents();
    }
    
    /**
     * 
     * @return
     */
    public final int index() {
        return index;
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.AbstractInputComponent#isBooleanComponent()
     */
    @Override
    public final boolean isBooleanComponent() {
        return true;
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.AbstractInputComponent#isSelectable()
     */
    @Override
    public final boolean isSelectable() {
        return true;
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.AbstractInputComponent#isStackable()
     */
    @Override
    public final boolean isStackable() {
        return true;
    }
}
