package org.iocaste.appbuilder.common;

import org.iocaste.appbuilder.common.tabletool.TableTool;
import org.iocaste.appbuilder.common.tabletool.TableToolData;
import org.iocaste.shell.common.AbstractValidator;
import org.iocaste.shell.common.Component;
import org.iocaste.shell.common.Element;
import org.iocaste.shell.common.InputComponent;
import org.iocaste.shell.common.Shell;
import org.iocaste.shell.common.Table;
import org.iocaste.shell.common.TableItem;

public abstract class AbstractExtendedValidator extends AbstractValidator {
    private static final long serialVersionUID = 8008598121724533805L;
    
    protected final TableItem getItem(String tablename, InputComponent input) {
        Element element;
        Component component;
        String text;
        Table table = getTable(tablename);
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
    
    protected final Table getTable(String name) {
        TableToolData data;
        PageBuilderContext context = getContext();
        ViewComponents components = context.
                getView(context.view.getPageName()).getComponents();
        
        data = components.getTableToolData(name);
        return TableTool.get(context.view, data);
    }
    
    protected abstract void validate(PageBuilderContext context);
    
    @Override
    public final void validate() {
        validate((PageBuilderContext)getContext());
    }
}
