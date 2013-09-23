package org.iocaste.shell.common;

import java.math.BigDecimal;

import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DocumentModelItem;

/**
 * Input component abstract implementation
 * 
 * If you want to create a new input component (such as text box), you
 * can do it from this implementation.
 * 
 * @author francisco.prates
 *
 */
public abstract class AbstractInputComponent extends AbstractComponent
    implements InputComponent {
    private static final long serialVersionUID = 7276777605716326451L;
    private Object value;
    private DocumentModelItem modelitem;
    private boolean secret, obligatory;
    private int length, vlength;
    private Const type;
    private DataElement dataelement;
    private SearchHelp search;
    private ValidatorConfig validatorcfg;
    
    public AbstractInputComponent(Container container, Const type,
            Const type_, String name) {
        super(container, type, name);
        
        this.type = type_;
        obligatory = false;
        validatorcfg = new ValidatorConfig();
        validatorcfg.add(this);
        setEventHandler(new OnFocus(this));
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.InputComponent#
     *     addValidatorInput(org.iocaste.shell.common.InputComponent)
     */
    @Override
    public final void addValidatorInput(InputComponent input) {
        validatorcfg.add(input);
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.InputComponent#get()
     */
    @SuppressWarnings("unchecked")
    @Override
    public final <T> T get() {
        return (T)value;
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
     * @see org.iocaste.shell.common.InputComponent#getd()
     */
    @Override
    public final double getd() {
        return ((BigDecimal)value).doubleValue();
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.InputComponent#getDataElement()
     */
    @Override
    public final DataElement getDataElement() {
        return dataelement;
    }
    
    /*
     * (não-Javadoc)
     * @see org.iocaste.shell.common.InputComponent#geti()
     */
    @Override
    public final int geti() {
        return ((BigDecimal)value).intValue();
    }
    
    /*
     * (não-Javadoc)
     * @see org.iocaste.shell.common.InputComponent#getl()
     */
    @Override
    public final long getl() {
        return ((BigDecimal)value).longValue();
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
     * @see org.iocaste.shell.common.InputComponent#getSearchHelp()
     */
    @Override
    public final SearchHelp getSearchHelp() {
        return search;
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.InputComponent#getStackComponents()
     */
    @Override
    public InputComponent[] getStackComponents() {
        return null;
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.InputComponent#getValidator()
     */
    @Override
    public final ValidatorConfig getValidatorConfig() {
        return validatorcfg;
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.InputComponent#getVisibleLength()
     */
    @Override
    public final int getVisibleLength() {
        return vlength;
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.InputComponent#isBooleanComponent()
     */
    @Override
    public boolean isBooleanComponent() {
        return false;
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.Element#isControlComponent()
     */
    public final boolean isControlComponent() {
        return false;
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
     * @see org.iocaste.shell.common.InputComponent#isSelectable()
     */
    @Override
    public boolean isSelectable() {
        return false;
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.InputComponent#isSelected()
     */
    @Override
    public final boolean isSelected() {
        Object value = get();
        
        return (value == null)? false : (Boolean)value;
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.InputComponent#isStackable()
     */
    @Override
    public boolean isStackable() {
        return false;
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.InputComponent#isValueRangeComponent()
     */
    @Override
    public boolean isValueRangeComponent() {
        return false;
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.InputComponent#set(java.lang.Object)
     */
    @Override
    public final void set(Object value) {
        this.value = value;
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.InputComponent#setComponentType(
     *     org.iocaste.shell.common.Const)
     */
    @Override
    public void setComponentType(Const type) {
        this.type = type;
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.InputComponent#setDataElement(
     *     org.iocaste.documents.common.DataElement)
     */
    @Override
    public final void setDataElement(DataElement dataelement) {
        this.dataelement = dataelement;
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.AbstractElement#setHtmlName(
     *     java.lang.String)
     */
    @Override
    public void setHtmlName(String name) {
        super.setHtmlName(name);
        
        if (search == null)
            return;
        
        search.setInputName(name);
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
     * @see org.iocaste.shell.common.InputComponent#setSearchHelp(
     *     org.iocaste.shell.common.SearchHelp)
     */
    @Override
    public final void setSearchHelp(SearchHelp search) {
        this.search = search;
        
        if (search == null)
            return;
        
        this.search.setInputName(getHtmlName());
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
     * @see org.iocaste.shell.common.InputComponent#setSelected(boolean)
     */
    @Override
    public final void setSelected(boolean selected) {
        set(selected);
    };
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.InputComponent#setValidator(
     *     java.lang.Class)
     */
    @Override
    public final void setValidator(Class<? extends Validator> validator) {
        validatorcfg.setValidator(validator);
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.InputComponent#setVisibleLength(int)
     */
    @Override
    public final void setVisibleLength(int vlength) {
        this.vlength = vlength;
    }
}

class OnFocus extends AbstractEventHandler {
    private static final long serialVersionUID = -6628615220035348184L;
    private Element element;
    
    public OnFocus(Element element) {
        this.element = element;
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.EventHandler#
     *     onEvent(byte, java.lang.String)
     */
    @Override
    public final void onEvent(byte event, String args) {
        element.getView().setFocus(element);
    }
}
