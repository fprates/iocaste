package org.iocaste.kernel.runtime.shell.dataformtool;

import org.iocaste.kernel.runtime.Runtime;
import org.iocaste.kernel.runtime.shell.SpecItemHandler;
import org.iocaste.runtime.common.page.ViewSpecItem.TYPES;

public class DataFormSpecItemHandler implements SpecItemHandler {
    
    @Override
    public void execute(Runtime shell, String name) {
    	shell.factories.get(TYPES.DATA_FORM).instance(name);
    }

}
