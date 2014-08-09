package org.iocaste.appbuilder.common;

import org.iocaste.appbuilder.common.tabletool.TableTool;
import org.iocaste.appbuilder.common.tabletool.TableToolData;
import org.iocaste.shell.common.AbstractContext;
import org.iocaste.shell.common.AbstractValidator;
import org.iocaste.shell.common.Table;

public abstract class AbstractExtendedValidator extends AbstractValidator {
    private static final long serialVersionUID = 8008598121724533805L;
    
    public AbstractExtendedValidator(String name, AbstractContext context) {
        super(name, context);
    }
    
    protected final Table getTable(String name) {
        PageBuilderContext context = getContext();
        TableToolData data = context.getViewComponents(
                context.view.getPageName()).getTableToolData(name);
        return TableTool.get(context.view, data);
    }
}
