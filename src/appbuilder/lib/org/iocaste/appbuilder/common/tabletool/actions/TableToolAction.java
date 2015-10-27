package org.iocaste.appbuilder.common.tabletool.actions;

import org.iocaste.appbuilder.common.tabletool.TableTool;
import org.iocaste.appbuilder.common.tabletool.TableToolData;
import org.iocaste.shell.common.AbstractContext;
import org.iocaste.shell.common.Button;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.ViewCustomAction;

public abstract class TableToolAction implements ViewCustomAction {
    private static final long serialVersionUID = 7220679345842901434L;
    private String action, name, text;
    private TableToolData data;
    protected TableTool tabletool;
    protected boolean navigable, markable;

    public TableToolAction(
            TableTool tabletool, TableToolData data, String action) {
        this(tabletool, data, action, true);
    }
    
    public TableToolAction(TableTool tabletool,
            TableToolData data, String action, boolean register) {
        name = action.concat(data.name);
        if (register)
            tabletool.getContext().function.register(name, this);
        
        this.tabletool = tabletool;
        this.action = action;
        this.data = data;
    }
    
    public final Button build(Container container) {
        Button button = new Button(container, action.concat(data.name));
        if (text != null)
            button.setText(text);
        return button;
    }
    
    @Override
    public abstract void execute(AbstractContext context) throws Exception;
    
    public final String getAction() {
        return action;
    }
    
    public final String getName() {
        return name;
    }
    
    public final boolean isMarkable() {
        return markable;
    }
    
    public final boolean isNavigable() {
        return navigable;
    }
    
    protected final void setMarkable(boolean markable) {
        this.markable = markable;
    }
    
    protected final void setNavigable(boolean navigable) {
        this.navigable = navigable;
    }
    
    protected final void setText(String text) {
        this.text = text;
    }
}
