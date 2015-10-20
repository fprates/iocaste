package org.iocaste.appbuilder.common;

import java.util.Set;

import org.iocaste.appbuilder.common.tabletool.TableToolItem;
import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.shell.common.AbstractContext;
import org.iocaste.shell.common.AbstractValidator;
import org.iocaste.shell.common.Element;
import org.iocaste.shell.common.InputComponent;
import org.iocaste.shell.common.Table;
import org.iocaste.shell.common.TableColumn;
import org.iocaste.shell.common.TableItem;

public abstract class AbstractExtendedValidator extends AbstractValidator {
    private String textmodel, textfield;
    private Documents documents;
    private PageBuilderContext context;
    
    /**
     * 
     * @return
     */
    @SuppressWarnings("unchecked")
    protected final <T extends AbstractContext> T getContext() {
        return (T)context;
    }
    
    /**
     * 
     * @param name
     * @return
     */
    protected final <T extends Element> T getElement(String name) {
        return context.view.getElement(name);
    }
    
    /**
     * 
     * @param table
     * @param input
     * @return
     */
    protected final TableToolItem getItem(String tname, InputComponent input) {
        Element element;
        Object value;
        TableToolItem ttitem;
        ViewComponents components;
        TableToolEntry entry;
        Set<TableItem> items;
        String name = input.getName();
        String htmlname = input.getHtmlName();
        ViewContext viewctx = context.getView(context.view.getPageName());
        
        if (viewctx == null)
            return null;
        
        components = viewctx.getComponents();
        entry = components.tabletools.get(tname);
        items = entry.component.getItems();
        if (items == null)
            return null;
        
        for (TableItem item : items) {
            if (item == null)
                continue;
            
            element = item.getElement(name);
            if (!element.getHtmlName().equals(htmlname))
                continue;

            value = input.get();
            if (!isItemElementMatch(element, value))
                continue;
            
            ttitem = new TableToolItem(entry.data);
            ttitem.set(item);
            ttitem.selected = item.isSelected();
            ttitem.object = entry.component.get(entry.data, item);
            
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
    public String getText(Object value) {
        ExtendedObject object;
        
        if (value == null)
            return null;

        if (textmodel == null)
            throw new RuntimeException(
                    "Texts source model undefined for validator.");
        
        object = documents.getObject(textmodel, value);
        if (object == null)
            return null;
        
        return object.getst(textfield);
    }
    
    public final void setDocuments(Documents documents) {
        this.documents = documents;
    }
    
    protected final void setInputs(TableToolItem ttitem) {
        String name;
        TableItem item = ttitem.get();
        Table table = item.getTable();
        
        for (TableColumn column : table.getColumns()) {
            if (column.isMark())
                continue;
            name = column.getName();
            setInput(item, name, ttitem.object.get(name));
        }
    }
    
    protected final void setTextReference(String model, String field) {
        textmodel = model;
        textfield = field;
    }
    
    /**
     * 
     * @param context
     */
    protected abstract void validate();
    
    /*
     * (não-Javadoc)
     * @see org.iocaste.shell.common.AbstractValidator#validate()
     */
    @Override
    public final void validate(AbstractContext context) {
        this.context = (PageBuilderContext)context;
        validate();
    }
}
