package org.iocaste.kernel.runtime.shell.dataformtool;

import org.iocaste.kernel.runtime.RuntimeEngine;
import org.iocaste.kernel.runtime.shell.SpecItemHandler;
import org.iocaste.runtime.common.page.ViewSpecItem.TYPES;

public class DataFormSpecItemHandler implements SpecItemHandler {
    
    @Override
    public void execute(RuntimeEngine shell, String name) {
    	shell.factories.get(TYPES.DATA_FORM).instance(name);
    }

}
