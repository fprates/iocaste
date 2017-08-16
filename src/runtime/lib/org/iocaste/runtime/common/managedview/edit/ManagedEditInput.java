package org.iocaste.runtime.common.managedview.edit;

import org.iocaste.runtime.common.application.Context;
import org.iocaste.runtime.common.page.AbstractViewInput;

public class ManagedEditInput extends AbstractViewInput<Context> {
    
    @Override
    protected void execute(Context context) {
        loadInputTexts();
    }
    
    protected void init(Context context) {
        execute(context);
    }
}
