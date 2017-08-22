package org.iocaste.runtime.common.managedview;

import org.iocaste.documents.common.ComplexModel;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.runtime.common.application.Context;
import org.iocaste.runtime.common.application.ToolData;
import org.iocaste.runtime.common.page.AbstractViewInput;

public class ManagedSelectInput extends AbstractViewInput<Context> {

    @Override
    protected void execute(Context context) {
        ManagedViewContext mviewctx = context.mviewctx();
        inputsetns("head", mviewctx.ns);
    }

    @Override
    protected void init(Context context) {
        ManagedViewContext mviewctx;
        ComplexModel cmodel;
        ToolData tooldata = context.getPage().instance("head");
        
        if (tooldata.object == null) {
            mviewctx = context.mviewctx();
            cmodel = context.runtime().getComplexModel(mviewctx.cmodel);
            tooldata.object = new ExtendedObject(cmodel.getHeader());
        }
        execute(context);
    }
    
}
