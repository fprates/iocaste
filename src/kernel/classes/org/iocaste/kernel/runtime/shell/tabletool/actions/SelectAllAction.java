package org.iocaste.kernel.runtime.shell.tabletool.actions;

import java.util.Map;

import org.iocaste.kernel.runtime.shell.tabletool.TableContext;
import org.iocaste.shell.common.AbstractContext;

public class SelectAllAction extends TableToolAction {

    public SelectAllAction(
            TableContext context, Map<String, TableToolAction> store) {
        super(context, store, "select_all");
        setMarkable(true);
        setText("pt_BR", "Marcar todos [☑]");
    }
    
    @Override
    public void execute(AbstractContext context) throws Exception {
        this.context.tabletool.selectAll(true);
    }

}
