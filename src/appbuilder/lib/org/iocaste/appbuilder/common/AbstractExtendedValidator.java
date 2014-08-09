package org.iocaste.appbuilder.common;

import org.iocaste.appbuilder.common.tabletool.TableTool;
import org.iocaste.appbuilder.common.tabletool.TableToolData;
import org.iocaste.shell.common.AbstractContext;
import org.iocaste.shell.common.AbstractValidator;
import org.iocaste.shell.common.Component;
import org.iocaste.shell.common.Element;
import org.iocaste.shell.common.InputComponent;
import org.iocaste.shell.common.Shell;
import org.iocaste.shell.common.Table;
import org.iocaste.shell.common.TableItem;

public abstract class AbstractExtendedValidator extends AbstractValidator {
    private static final long serialVersionUID = 8008598121724533805L;
    
    public AbstractExtendedValidator(String name, AbstractContext context) {
        super(name, context);
    }
    
    protected final TableItem getItem(String tablename, InputComponent input) {
        Element element;
        Component component;
        String text;
        String name = input.getName();
        Table table = getTable(tablename);
        
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
        PageBuilderContext context = getContext();
        TableToolData data = context.getViewComponents(
                context.view.getPageName()).getTableToolData(name);
        return TableTool.get(context.view, data);
    }
}
