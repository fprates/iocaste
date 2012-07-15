package org.iocaste.datadict;

import org.iocaste.protocol.Function;
import org.iocaste.shell.common.Table;
import org.iocaste.shell.common.View;

public class Add {

    /**
     * 
     * @param view
     * @param function
     */
    public static final void main(View view, Function function) {
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
