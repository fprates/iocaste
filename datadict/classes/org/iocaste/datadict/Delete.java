package org.iocaste.datadict;

import org.iocaste.shell.common.Table;
import org.iocaste.shell.common.TableItem;
import org.iocaste.shell.common.View;

public class Delete {
    
    /**
     * 
     * @param view
     */
    public static final void shitem(View view) {
        Table itens = view.getElement("itens");
        
        for (TableItem item : itens.getItens()) {
            if (!item.isSelected())
                continue;
            
            itens.remove(item);
        }
    }

}
