package org.iocaste.kernel.runtime.shell.tabletool;

import java.util.Map;

import org.iocaste.kernel.runtime.shell.tabletool.actions.AbstractTableToolAction;
import org.iocaste.shell.common.ControlComponent;

public class CustomAction extends AbstractTableToolAction {
    private static final long serialVersionUID = -4593652904996864655L;

    public CustomAction(TableContext context,
            Map<String, AbstractTableToolAction> store, String action) {
        super(context, store, action, false);
    }

    @Override
    public void onEvent(ControlComponent control) {
        // TODO Auto-generated method stub
        
    }

}
