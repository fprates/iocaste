package org.iocaste.upload;

import org.iocaste.docmanager.common.AbstractManager;
import org.iocaste.protocol.Function;

public class LayoutManager extends AbstractManager {

    public LayoutManager(Function function) {
        super("UPL_LAYOUT", function);
        setItemDigits("columns", 4);
    }

}
