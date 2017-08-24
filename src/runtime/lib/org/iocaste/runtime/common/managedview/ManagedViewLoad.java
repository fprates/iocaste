package org.iocaste.runtime.common.managedview;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.runtime.common.application.AbstractActionHandler;
import org.iocaste.runtime.common.application.Context;
import org.iocaste.shell.common.Const;

public class ManagedViewLoad extends AbstractActionHandler<Context> {
    private Map<String, String> pages;
    
    @Override
    protected void execute(Context context) {
        ExtendedObject object;
        String table, redirect;
        ManagedViewContext mviewctx = context.mviewctx();
        
        mviewctx.id = getkey("head");
        mviewctx.ns = getns("head");
        mviewctx.document = getcdocument(
                mviewctx.cmodel, mviewctx.ns, mviewctx.id);
        if (mviewctx.document == null)
            message(Const.ERROR, "invalid.code");

        if (pages == null) {
            pages = new HashMap<>();
            pages.put(mviewctx.createview, mviewctx.create1view);
            pages.put(mviewctx.displayview, mviewctx.display1view);
            pages.put(mviewctx.editview, mviewctx.edit1view);
        }
        
        init(redirect = pages.get(context.getPageName()));
        object = mviewctx.document.getHeader();
        context.set(redirect, "head", object);
        context.set(redirect, "base", object);
        for (String name : mviewctx.document.getModel().getItems().keySet()) {
            table = name.concat("_table");
            context.set(redirect, table, mviewctx.document.getItems(name));
        }
        redirect(redirect);
    }

}
