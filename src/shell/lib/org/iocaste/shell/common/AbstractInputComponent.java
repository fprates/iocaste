package org.iocaste.shell.common;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

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
    private Object value, ns;
    private DocumentModelItem modelitem;
    private boolean secret, obligatory;
    private int length, vlength, error;
    private Const type;
    private DataElement dataelement;
    private String master, nsreference, label, calendar, search;
    private byte[] content;
    private boolean placeholder;
    private boolean nohelper;
    
    public AbstractInputComponent(View view, Const type, Const type_,
            String name) {
        super(view, type, name);
        init(type_);
    }
    
    public AbstractInputComponent(Container container, Const type,
            Const type_, String name) {
        super(container, type, name);
        init(type_);
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
     * (não-Javadoc)
     * @see org.iocaste.shell.common.InputComponent#getCalendar()
     */
    @Override
    public final Calendar getCalendar() {
        return (calendar == null)? null : getView().getElement(calendar);
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.InputComponent#getComponentType()
     */
    @Override
    public final Const getComponentType() {
        return type;
    }

    public final byte[] getContent() {
        return content;
    }

    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.InputComponent#getd()
     */
    @Override
    public final double getd() {
        if (!(value instanceof BigDecimal))
            return (value == null)? 0 : (double)value;
        
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
     * @see org.iocaste.shell.common.InputComponent#getb()
     */
    @Override
    public final byte getb() {
        if (!(value instanceof BigDecimal))
            return (value == null)? 0 : (byte)value;
        
        return ((BigDecimal)value).byteValue();
    }
    
    /*
     * (não-Javadoc)
     * @see org.iocaste.shell.common.InputComponent#getdt()
     */
    @Override
    public final Date getdt() {
        return (Date)value;
    }
    
    public final int getError() {
        return error;
    }
    
    /*
     * (não-Javadoc)
     * @see org.iocaste.shell.common.InputComponent#geti()
     */
    @Override
    public final int geti() {
        if (!(value instanceof BigDecimal))
            return (value == null)? 0 : (int)value;
        
        return ((BigDecimal)value).intValue();
    }
    
    /*
     * (não-Javadoc)
     * @see org.iocaste.shell.common.InputComponent#getl()
     */
    @Override
    public final long getl() {
        if (!(value instanceof BigDecimal))
            return (value == null)? 0 : (long)value;
        
        return ((BigDecimal)value).longValue();
    }

    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.InputComponent#getLabel()
     */
    @Override
    public final String getLabel() {
        return label;
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
     * (não-Javadoc)
     * @see org.iocaste.shell.common.InputComponent#getMaster()
     */
    @Override
    public final String getMaster() {
        return master;
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
     * @see org.iocaste.shell.common.InputComponent#getNS()
     */
    @Override
    public final Object getNS() {
        return ns;
    }
    
    /*
     * (não-Javadoc)
     * @see org.iocaste.shell.common.InputComponent#getNSReference()
     */
    @Override
    public final String getNSReference() {
        return nsreference;
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.InputComponent#getSearchHelp()
     */
    @Override
    public final SearchHelp getSearchHelp() {
        return (search == null)? null : getView().getElement(search);
    }
    
    /*
     * (não-Javadoc)
     * @see org.iocaste.shell.common.InputComponent#getst()
     */
    @Override
    public final String getst() {
        return (String)value;
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.InputComponent#getStackComponents()
     */
    @Override
    public Set<InputComponent> getStackComponents() {
        return null;
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
     * 
     */
    @Override
    public final boolean hasNoHelper() {
        return nohelper;
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.InputComponent#hasPlaceHolder()
     */
    @Override
    public final boolean hasPlaceHolder() {
        return placeholder;
    }
    
    /**
     * 
     * @param type
     */
    private final void init(Const type) {
        this.type = type;
        obligatory = false;
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
    public void set(Object value) {
        this.value = value;
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.InputComponent#set(
     *     java.lang.Object, java.lang.Object)
     */
    @Override
    public void set(Object ns, Object value) {
        this.ns = ns;
        this.value = value;
    }
    
    /*
     * (não-Javadoc)
     * @see org.iocaste.shell.common.InputComponent#setCalendar(
     *    org.iocaste.shell.common.Calendar)
     */
    @Override
    public final void setCalendar(Calendar calendar) {
        if (calendar == null) {
            this.calendar = null;
            return;
        }
        this.calendar = calendar.getHtmlName();
        getCalendar().setInputName(getHtmlName());
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
    
    public final void setContent(byte[] content) {
        this.content = content;
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
    
    public final void setError(int error) {
        this.error = error;
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
        getSearchHelp().setInputName(name);
        getCalendar().setInputName(name);
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.InputComponent#setLabel(
     *     java.lang.String)
     */
    @Override
    public final void setLabel(String label) {
        this.label = label;
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.InputComponent#setLength(int)
     */
    @Override
    public final void setLength(int length) {
        this.length = length;
    }
    
    /**
     * 
     * @param master
     */
    protected final void setMaster(String master) {
        this.master = master;
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
     * 
     */
    @Override
    public final void setNoHelper(boolean nohelper) {
        this.nohelper = nohelper;
    }
    
    /**
     * 
     * @param ns
     */
    protected final void setNS(Object ns) {
        this.ns = ns;
    }
    
    /*
     * (não-Javadoc)
     * @see org.iocaste.shell.common.InputComponent#setNSReference(
     *    java.lang.String)
     */
    @Override
    public final void setNSReference(String nsreference) {
        this.nsreference = nsreference;
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.InputComponent#setObligatory(boolean)
     */
    @Override
    public final void setObligatory(boolean obligatory) {
        this.obligatory = obligatory;
    }
    
    @Override
    public final void setPlaceHolder(boolean placeholder) {
        this.placeholder = placeholder;
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.InputComponent#setSearchHelp(
     *     org.iocaste.shell.common.SearchHelp)
     */
    @Override
    public final void setSearchHelp(SearchHelp search) {
        if (search == null) {
            this.search = null;
            return;
        }
        this.search = search.getHtmlName();
        getSearchHelp().setInputName(getHtmlName());
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
     * @see org.iocaste.shell.common.InputComponent#setVisibleLength(int)
     */
    @Override
    public final void setVisibleLength(int vlength) {
        this.vlength = vlength;
    }
    
    @Override
    public String toString() {
        Object value = get();
        if (value == null)
            value = "null";
        
        return new StringBuilder(getHtmlName()).
                append("=").
                append(value.toString()).toString();
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.AbstractComponent#translate(
     *    org.iocaste.shell.common.MessageSource)
     */
    @Override
    public void translate(MessageSource messages) {
        if (!isTranslatable())
            return;
        if (label == null)
            label = getTranslation(messages, getName());
        else
            label = messages.get(label);
        if (label == null)
            label = getTranslation(messages, getName());
    }
}
