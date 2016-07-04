package org.iocaste.appbuilder.common;

import java.util.Set;

import org.iocaste.appbuilder.common.dataformtool.DataFormTool;
import org.iocaste.appbuilder.common.tabletool.TableTool;
import org.iocaste.appbuilder.common.tabletool.TableToolData;
import org.iocaste.appbuilder.common.tabletool.TableToolItem;
import org.iocaste.documents.common.Documents;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.shell.common.AbstractContext;
import org.iocaste.shell.common.AbstractValidator;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.Element;
import org.iocaste.shell.common.InputComponent;
import org.iocaste.shell.common.TableItem;

public abstract class AbstractExtendedValidator extends AbstractValidator {
    private String textmodel, textfield;
    private Documents documents;
    private PageBuilderContext context;
    
    /**
     * 
     * @param name
     * @return
     */
    protected final ExtendedObject dfget(String name) {
        DataFormTool dftool = context.getView().getComponents().
                getComponent(name);
        return dftool.getObject();
    }
    
    /**
     * 
     * @param name
     * @param object
     */
    protected final void dfset(String name, ExtendedObject object) {
        DataFormTool dftool = context.getView().getComponents().
                getComponent(name);
        dftool.setObject(object);
    }
    
    /**
     * 
     * @return
     */
    protected final PageBuilderContext getContext() {
        return context;
    }
    
    protected final <T extends ExtendedContext> T getExtendedContext() {
        return context.getView().getExtendedContext();
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
        Set<TableItem> items;
        TableTool tabletool;
        TableToolData ttdata;
        int i;
        String name = input.getName();
        String htmlname = input.getHtmlName();
        ViewContext viewctx = context.getView();
        
        if (viewctx == null)
            return null;
        
        components = viewctx.getComponents();
        tabletool = components.getComponent(tname);
        ttdata = components.getComponentData(tname);
        items = tabletool.getItems();
        if (items == null)
            return null;
        
        i = 0;
        for (TableItem item : items) {
            i++;
            if (item == null)
                continue;
            
            element = item.getElement(name);
            if (!element.getHtmlName().equals(htmlname))
                continue;

            value = input.get();
            if (!isItemElementMatch(element, value))
                continue;
            
            ttitem = new TableToolItem(ttdata);
            ttitem.set(item);
            ttitem.selected = item.isSelected();
            ttitem.object = tabletool.get(ttdata, item);
            ttitem.position = ttdata.topline + i - 1;
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
    
    protected final void setFocus(String name) {
        context.view.setFocus(context.view.getElement(name));
    }
    
    protected final void setFocus(String parent, String name) {
        AbstractComponentTool tool;
        Container container;
        
        tool = context.getView().getComponents().getComponent(parent);
        container = tool.getElement();
        context.view.setFocus(container.getElement(name));
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
