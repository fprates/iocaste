package org.iocaste.appbuilder.common;

import java.util.List;

import org.iocaste.appbuilder.common.tabletool.TableToolItem;
import org.iocaste.shell.common.AbstractValidator;
import org.iocaste.shell.common.Element;
import org.iocaste.shell.common.InputComponent;
import org.iocaste.shell.common.Table;
import org.iocaste.shell.common.TableColumn;

public abstract class AbstractExtendedValidator extends AbstractValidator {
    
    /**
     * 
     * @param table
     * @param input
     * @return
     */
    protected final TableToolItem getItem(String tname, InputComponent input) {
        Element element;
        Object value;
        String name = input.getName();
        String htmlname = input.getHtmlName();
        PageBuilderContext context = getContext();
        ViewComponents components = context.
                getView(context.view.getPageName()).getComponents();
        TableToolEntry entry = components.tabletools.get(tname);
        List<TableToolItem> ttitems = entry.data.getItems();
        
        if (ttitems == null)
            return null;
        
        for (TableToolItem ttitem : ttitems) {
            element = ttitem.item.getElement(name);
            if (!element.getHtmlName().equals(htmlname))
                continue;

            value = input.get();
            if (!isItemElementMatch(element, value))
                continue;
            return ttitem;
        }
        
        return null;
    }
    
    /**
     * 
     * @param context
     * @param value
     * @return
     */
    public String getText(PageBuilderContext context, Object value) {
        return null;
    }
    
    protected final void setInputs(TableToolItem ttitem) {
        String name;
        Table table = ttitem.item.getTable();
        
        for (TableColumn column : table.getColumns()) {
            if (column.isMark())
                continue;
            name = column.getName();
            setInput(ttitem.item, name, ttitem.object.get(name));
        }
    }
    
    /**
     * 
     * @param context
     */
    protected abstract void validate(PageBuilderContext context);
    
    /*
     * (não-Javadoc)
     * @see org.iocaste.shell.common.AbstractValidator#validate()
     */
    @Override
    public final void validate() {
        validate((PageBuilderContext)getContext());
    }
}
