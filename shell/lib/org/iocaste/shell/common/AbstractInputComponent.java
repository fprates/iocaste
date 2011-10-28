package org.iocaste.shell.common;

import org.iocaste.documents.common.DataType;
import org.iocaste.documents.common.DocumentModelItem;

public abstract class AbstractInputComponent extends AbstractComponent
    implements InputComponent {
    private static final long serialVersionUID = 7276777605716326451L;
    private String value;
    private DocumentModelItem modelitem;
    private boolean obligatory;
    private int length;
    private Const type;
    private boolean secret;
    
    public AbstractInputComponent(Container container, Const type,
            Const type_, String name) {
        super(container, type, name);

        this.type = type_;
        obligatory = false;
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.InputComponent#getComponentType()
     */
    @Override
    public final Const getComponentType() {
        return type;
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.InputComponent#getLength()
     */
    @Override
    public final int getLength() {
        return length;
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
     * @see org.iocaste.shell.common.InputComponent#isSecret()
     */
    @Override
    public final boolean isSecret() {
        return secret;
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.InputComponent#getParsedValue()
     */
    @Override
    public final Object getParsedValue() {
        switch (modelitem.getDataElement().getType()) {
        case DataType.CHAR:
            return value;
        case DataType.DEC:
            return Double.parseDouble(value);
        case DataType.NUMC:
            return Integer.parseInt(value);
        default:
            return null;
        }
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.InputComponent#setLength(int)
     */
    @Override
    public final void setLength(int length) {
        this.length = length;
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
     * @see org.iocaste.shell.common.InputComponent#setSecret(boolean)
     */
    @Override
    public final void setSecret(boolean secret) {
        this.secret = secret;
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
