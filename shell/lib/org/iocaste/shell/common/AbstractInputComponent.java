package org.iocaste.shell.common;

import org.iocaste.documents.common.DocumentModelItem;

public abstract class AbstractInputComponent extends AbstractComponent
    implements InputComponent {
    private static final long serialVersionUID = 7276777605716326451L;
    private String value;
    private DocumentModelItem modelitem;
    private boolean obligatory;
    
    public AbstractInputComponent(Container container, Const type, String name) {
        super(container, type, name);
        
        obligatory = false;
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.InputComponent#getModelItem()
     */
    @Override
    public final DocumentModelItem getModelItem() {
        return modelitem;
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.DataComponent#getValue()
     */
    @Override
    public String getValue() {
        return value;
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.Element#isDataStorable()
     */
    @Override
    public final boolean isDataStorable() {
        return true;
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.InputComponent#isObligatory()
     */
    @Override
    public final boolean isObligatory() {
        return obligatory;
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.Component#setModelItem(
     *     org.iocaste.documents.common.DocumentModelItem)
     */
    @Override
    public final void setModelItem(DocumentModelItem modelitem) {
        this.modelitem = modelitem;
    }

    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.InputComponent#setObligatory(boolean)
     */
    @Override
    public final void setObligatory(boolean obligatory) {
        this.obligatory = obligatory;
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.DataComponent#setValue(java.lang.String)
     */
    @Override
    public void setValue(String value) {
        this.value = value;
    }

}
