package org.iocaste.appbuilder.common.tabletool.actions;

import org.iocaste.appbuilder.common.tabletool.TableTool;
import org.iocaste.appbuilder.common.tabletool.TableToolData;
import org.iocaste.shell.common.AbstractContext;
import org.iocaste.shell.common.AbstractPage;
import org.iocaste.shell.common.ViewCustomAction;

public abstract class TableToolAction implements ViewCustomAction {
    private static final long serialVersionUID = 7220679345842901434L;
    protected String action, name;
    protected TableTool tabletool;
    
    public TableToolAction(
            TableTool tabletool, TableToolData data, String action) {
        name = action.concat(data.name);
        ((AbstractPage)tabletool.getContext().function).register(name, this);
        
        this.tabletool = tabletool;
        this.action = action;
    }
    
    @Override
    public abstract void execute(AbstractContext context) throws Exception;
    
    public String getName() {
        return name;
    }
}
