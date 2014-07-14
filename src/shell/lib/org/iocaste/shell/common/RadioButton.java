package org.iocaste.shell.common;

/**
 * Implementação de componente radio button.
 * 
 * @author francisco.prates
 *
 */
public class RadioButton extends AbstractInputComponent {
    private static final long serialVersionUID = 4032308949086603543L;
    private RadioGroup group;
    private int index;
    
    public RadioButton(View view, String name, RadioGroup group) {
        super(view, Const.RADIO_BUTTON, null, name);
        init(group);
    }
    
    public RadioButton(Container container, String name, RadioGroup group) {
        super(container, Const.RADIO_BUTTON, null, name);
        init(group);
    }
    
    /**
     * Retorna grupo de botões.
     * @return grupo de botões.
     */
    public final RadioGroup getGroup() {
        return group;
    }
    
    /**
     * Indica que componente do grupo foi selecionado.
     * @return botão selecionado.
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
     * Retorna índice do botão no grupo.
     * @return índice.
     */
    public final int index() {
        return index;
    }
    
    private final void init(RadioGroup group) {
        this.group = group;
        index = group.add(this);
        
        setHtmlName(group.getName());
        setSelected(false);
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
