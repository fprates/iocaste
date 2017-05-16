package org.iocaste.kernel.runtime.shell.tabletool.actions;

import java.util.Map;

import org.iocaste.kernel.runtime.shell.tabletool.TableContext;
import org.iocaste.shell.common.AbstractContext;

public class DeselectAllAction extends TableToolAction {

    public DeselectAllAction(
            TableContext context, Map<String, TableToolAction> store) {
        super(context, store, "deselect_all");
        setMarkable(true);
        setText("pt_BR", "Desmarcar todos [☐]");
    }
    
    @Override
    public void execute(AbstractContext context) throws Exception {
        this.context.tabletool.selectAll(false);
    }

}
