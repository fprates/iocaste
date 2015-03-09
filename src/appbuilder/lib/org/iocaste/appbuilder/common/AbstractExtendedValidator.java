package org.iocaste.appbuilder.common;

import org.iocaste.appbuilder.common.tabletool.TableTool;
import org.iocaste.shell.common.AbstractValidator;
import org.iocaste.shell.common.Component;
import org.iocaste.shell.common.Element;
import org.iocaste.shell.common.InputComponent;
import org.iocaste.shell.common.Shell;
import org.iocaste.shell.common.TableItem;

public abstract class AbstractExtendedValidator extends AbstractValidator {
    private static final long serialVersionUID = 8008598121724533805L;
    
    /**
     * 
     * @param tablename
     * @param input
     * @return
     */
    protected final TableItem getItem(
            String tablename, InputComponent input) {
        return getItem(getTable(tablename), input);
    }
    
    /**
     * 
     * @param table
     * @param input
     * @return
     */
    protected final TableItem getItem(TableTool table, InputComponent input) {
        Element element;
        Component component;
        String text;
        String name = input.getName();
        
        for (TableItem item : table.getItems()) {
            element = item.getElement(name);
            if (!element.getHtmlName().equals(input.getHtmlName()))
                continue;
            
            if (element.isContainable() || element.isControlComponent())
                continue;
            
            if (element.isDataStorable()) {
                input = (InputComponent)element;
                if (Shell.areEquals(input, input.get()))
                    return item;
                continue;
            }
            
            component = (Component)element;
            text = component.getText();
            if (text.equals(input.get()))
                return item;
        }
        
        return null;
    }
    
    /**
     * 
     * @param name
     * @return
     */
    protected final TableTool getTable(String name) {
        PageBuilderContext context = getContext();
        ViewComponents components = context.
                getView(context.view.getPageName()).getComponents();
        
        return components.tabletools.get(name).component;
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
