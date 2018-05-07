package org.iocaste.shell.common;

import java.util.Map;

import org.iocaste.shell.common.tooldata.Context;
import org.iocaste.shell.common.tooldata.ElementViewContext;
import org.iocaste.shell.common.tooldata.ToolDataElement;
import org.iocaste.shell.common.tooldata.ViewSpecItem.TYPES;

/**
 * Item de formulário de dados.
 * 
 * @author francisco.prates
 *
 */
public class DataItem extends ToolDataElement {
    private static final long serialVersionUID = 3376883855229003535L;
    
    public DataItem(DataForm form, Const type, String name) {
        this(new ElementViewContext(null, form, TYPES.DUMMY, name),
                type, name);
    }
    
    public DataItem(Context viewctx, Const type, String name) {
        super(viewctx, type, name);
        setHtmlName(new StringBuilder(getContainer().getName()).
                append(".").
                append(name).toString());
        tooldata.styles.put("cell", "form_cell");
    }
    
    /**
     * 
     * @param key
     * @param value
     */
    public final void add(String key, Object value) {
        tooldata.values.put(key, value);
    }
    
    /**
     * 
     */
    public final void clear() {
        tooldata.clear();
    }
    
    /**
     * 
     * @return
     */
    public final Map<String, Object> getValues() {
        return tooldata.values;
    }
    
    @Override
    public final boolean hasMultipartSupport() {
        return (tooldata.componenttype == Const.FILE_ENTRY);
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.tooldata.ToolDataElement#
     *    isBooleanComponent()
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
     * @see org.iocaste.shell.common.AbstractInputComponent#setHtmlName(
     *    java.lang.String)
     */
    @Override
    public final void setHtmlName(String name) {
        String htmlname;

        super.setHtmlName(name);
        getContainer().add(this);
        htmlname = getHtmlName();
        lowname = htmlname.concat(".low");
        highname = htmlname.concat(".high");
    }
}
