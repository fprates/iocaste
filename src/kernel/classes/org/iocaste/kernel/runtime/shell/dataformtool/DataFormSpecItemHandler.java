package org.iocaste.kernel.runtime.shell.dataformtool;

import org.iocaste.kernel.runtime.shell.SpecItemHandler;
import org.iocaste.kernel.runtime.shell.ViewContext;
import org.iocaste.runtime.common.page.ViewSpecItem.TYPES;

public class DataFormSpecItemHandler implements SpecItemHandler {
    
    @Override
    public void execute(ViewContext viewctx, String name) {
    	viewctx.factories.get(TYPES.DATA_FORM).instance(name);
    }

}
