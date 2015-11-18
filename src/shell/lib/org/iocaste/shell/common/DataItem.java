package org.iocaste.shell.common;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Item de formulário de dados.
 * 
 * @author francisco.prates
 *
 */
public class DataItem extends AbstractInputComponent
        implements RangeInputComponent, MultipartElement {
    private static final long serialVersionUID = 3376883855229003535L;
    private Map<String, Object> values;
    private String highname, lowname, label;
    
    public DataItem(DataForm form, Const type, String name) {
        super(form, Const.DATA_ITEM, type, name);
        
        values = new LinkedHashMap<String, Object>();
        setStyleClass("form_cell");
        setLength(20);
        setHtmlName(new StringBuilder(form.getName()).
                append(".").
                append(name).toString());
    }
    
    /**
     * 
     * @param key
     * @param value
     */
    public final void add(String key, Object value) {
        values.put(key, value);
    }
    
    /**
     * 
     */
    public final void clear() {
        values.clear();
    }

    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.RangeInputComponent#getHighHtmlName()
     */
    @Override
    public final String getHighHtmlName() {
        return highname;
    }

    /**
     * 
     * @return
     */
    public final String getLabel() {
        return label;
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.RangeInputComponent#getLowHtmlName()
     */
    @Override
    public final String getLowHtmlName() {
        return lowname;
    }
    
    /**
     * 
     * @return
     */
    public final Map<String, Object> getValues() {
        return values;
    }
    
    @Override
    public boolean hasMultipartSupport() {
        return (getComponentType() == Const.FILE_ENTRY);
    };
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.AbstractInputComponent#isBooleanComponent()
     */
    @Override
    public final boolean isBooleanComponent() {
        switch (getComponentType()) {
        case CHECKBOX:
        case RADIO_BUTTON:
            return true;
        default:
            return false;
        }
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.AbstractInputComponent#isSelectable()
     */
    @Override
    public final boolean isSelectable() {
        return isBooleanComponent();
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.AbstractInputComponent#
     *     isValueRangeComponent()
     */
    @Override
    public final boolean isValueRangeComponent() {
        return (getComponentType() == Const.RANGE_FIELD);
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.AbstractInputComponent#setHtmlName(java.lang.String)
     */
    @Override
    public final void setHtmlName(String name) {
        String htmlname;
        super.setHtmlName(name);
        
        htmlname = getHtmlName();
        lowname = htmlname.concat(".low");
        highname = htmlname.concat(".high");
    }
    
    /**
     * 
     * @param label
     */
    public final void setLabel(String label) {
        this.label = label;
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.AbstractComponent#translate(
     *    org.iocaste.shell.common.MessageSource)
     */
    @Override
    public final void translate(MessageSource messages) {
        if (isTranslatable())
            this.label = getTranslation(messages, getName());
    }
}
