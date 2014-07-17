package org.iocaste.shell.common;

import java.util.Set;

/**
 * Implementação de componente radio button.
 * 
 * @author francisco.prates
 *
 */
public class RadioButton extends AbstractInputComponent {
    private static final long serialVersionUID = 4032308949086603543L;
    private int index;
    private RadioGroup group;
    
    public RadioButton(RadioGroup group, Container container, String name,
            int index) {
        super(container, Const.RADIO_BUTTON, null, name);
        this.index = index;
        this.group = group;
        
        group.rename(getHtmlName(), name);
        setSelected(false);
    }
    
    /**
     * 
     * @return
     */
    public final RadioGroup getGroup() {
        return group;
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.AbstractInputComponent#getStackComponents()
     */
    @Override
    public final Set<InputComponent> getStackComponents() {
        return group.getComponents();
    }
    
    /**
     * Retorna índice do botão no grupo.
     * @return índice.
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
    
    /*
     * (não-Javadoc)
     * @see org.iocaste.shell.common.AbstractInputComponent#setHtmlName(
     *    java.lang.String)
     */
    @Override
    public final void setHtmlName(String htmlname) {
        if (group != null)
            group.rename(htmlname, getHtmlName());
        super.setHtmlName(htmlname);
    }
}
