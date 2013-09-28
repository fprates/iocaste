package org.iocaste.datadict;

import org.iocaste.shell.common.Table;

public class Add {

    public static final void main(Context context) {
        ItemConfig config;
        
        if (Common.hasItemDuplicated(context.view))
            return;

        config = new ItemConfig();
        config.setTable((Table)context.view.getElement("itens"));
        config.setMode(context.mode);
        config.setView(context.view);
        config.setReferences(Common.getFieldReferences(context.function));
        
        Common.insertItem(config);
    }
}
