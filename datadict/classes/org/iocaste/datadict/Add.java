package org.iocaste.datadict;

import org.iocaste.protocol.Function;
import org.iocaste.shell.common.Table;
import org.iocaste.shell.common.ViewData;

public class Add {

    /**
     * 
     * @param view
     * @param function
     * @throws Exception
     */
    public static final void main(ViewData view, Function function)
            throws Exception {
        ItemConfig config;
        byte mode = Common.getMode(view);
        
        if (Common.hasItemDuplicated(view))
            return;

        config = new ItemConfig();
        config.setTable((Table)view.getElement("itens"));
        config.setMode(mode);
        config.setView(view);
        config.setReferences(Common.getFieldReferences(function));
        
        Common.insertItem(config);
    }
}
