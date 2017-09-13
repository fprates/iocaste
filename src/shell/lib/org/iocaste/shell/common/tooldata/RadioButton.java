package org.iocaste.shell.common.tooldata;

import java.util.Set;

import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.InputComponent;

/**
 * Implementação de componente radio button.
 * 
 * @author francisco.prates
 *
 */
public class RadioButton extends ToolDataElement {
	private static final long serialVersionUID = 8631968741416578644L;
	private int index;
    private String group;
    
    public RadioButton(
            Context viewctx, RadioGroup group, String name, int index) {
        super(viewctx, Const.RADIO_BUTTON, name);
        this.group = group.getHtmlName();
        this.index = index;
        setStyleClass(group.getButtonStyleClass());
        
        group.rename(getHtmlName(), name);
        tooldata.value = false;
    }
    
    /**
     * 
     * @return
     */
    public final RadioGroup getGroup() {
        return getView().getElement(group);
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.AbstractInputComponent#
     *    getStackComponents()
     */
    @Override
    public final Set<InputComponent> getStackComponents() {
        return getGroup().getComponents();
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
     * @see org.iocaste.shell.common.AbstractInputComponent#
     *   isBooleanComponent()
     */
    @Override
    public final boolean isBooleanComponent() {
        return true;
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.kernel.runtime.shell.elements.ToolDataElement#
     *   isDataStorable()
     */
    @Override
    public final boolean isDataStorable() {
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
     * @see org.iocaste.shell.common.AbstractInputComponent#
     *    setHtmlName(java.lang.String)
     */
    @Override
    public final void setHtmlName(String htmlname) {
        if (group != null)
            getGroup().rename(htmlname, getHtmlName());
        super.setHtmlName(htmlname);
    }
}
