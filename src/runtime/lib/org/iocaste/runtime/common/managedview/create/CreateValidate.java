package org.iocaste.runtime.common.managedview.create;

import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.runtime.common.application.AbstractActionHandler;
import org.iocaste.runtime.common.application.Context;
import org.iocaste.runtime.common.managedview.ManagedViewContext;
import org.iocaste.runtime.common.page.AbstractPage;
import org.iocaste.shell.common.Const;

public class CreateValidate extends AbstractActionHandler<Context> {
    
    @Override
    protected void execute(Context context) {
        AbstractPage page;
        ExtendedObject object = getobject("head");
        ManagedViewContext mviewctx = context.mviewctx();
        
        mviewctx.id = getkey("head");
        mviewctx.ns = object.getNS();
        mviewctx.document = null;
        
        if (getcdocument(mviewctx.cmodel, mviewctx.id) != null)
            message(Const.ERROR, "document.already.exists");

        init(mviewctx.redirect);
        page = context.getPage(mviewctx.redirect);
        page.instance("head");
        context.set(mviewctx.redirect, "head", object);
        page.instance("base");
        context.set(mviewctx.redirect, "base", object);
        redirect(mviewctx.redirect);
    }

}
