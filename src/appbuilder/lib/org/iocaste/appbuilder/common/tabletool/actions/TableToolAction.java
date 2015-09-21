package org.iocaste.appbuilder.common.tabletool.actions;

import org.iocaste.appbuilder.common.tabletool.TableTool;
import org.iocaste.appbuilder.common.tabletool.TableToolData;
import org.iocaste.shell.common.AbstractContext;
import org.iocaste.shell.common.AbstractPage;
import org.iocaste.shell.common.Button;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.ViewCustomAction;

public abstract class TableToolAction implements ViewCustomAction {
    private static final long serialVersionUID = 7220679345842901434L;
    private String action, name, text;
    private TableToolData data;
    protected TableTool tabletool;
    
    public TableToolAction(
            TableTool tabletool, TableToolData data, String action) {
        name = action.concat(data.name);
        ((AbstractPage)tabletool.getContext().function).register(name, this);
        
        this.tabletool = tabletool;
        this.action = action;
        this.data = data;
    }
    
    public Button build(Container container) {
        Button button = new Button(container, action.concat(data.name));
        if (text != null)
            button.setText(text);
        return button;
    }
    
    @Override
    public abstract void execute(AbstractContext context) throws Exception;
    
    public String getAction() {
        return action;
    }
    
    public String getName() {
        return name;
    }
    
    protected void setText(String text) {
        this.text = text;
    }
}
