package org.iocaste.runtime.common.managedview;

import org.iocaste.runtime.common.application.Context;
import org.iocaste.runtime.common.managedview.display.ManagedDisplayPage;
import org.iocaste.runtime.common.managedview.edit.ManagedEditPage;

public class ManagedViewFactory {
    
    public final void build(Context context, ManagedViewContext mviewctx) {
        mviewctx.redirect = (mviewctx.number == null)?
                mviewctx.create1view : mviewctx.edit1view;
        
        if (mviewctx.inputvalidate == null)
            mviewctx.inputvalidate = new ManagedInputValidate();

        if (mviewctx.entitypage == null)
            mviewctx.entitypage = new ManagedEntityPage();
        
        for (String action : new String[] {
                ManagedViewContext.CREATE,
                ManagedViewContext.DISPLAY,
                ManagedViewContext.EDIT}) {
            if ((mviewctx.number != null) &&
                    action.equals(ManagedViewContext.CREATE) &&
                    (mviewctx.entitypage.getConfig() == null))
                continue;
            mviewctx.entitypage.action = action;
            if (mviewctx.entitypage.spec == null)
                mviewctx.entitypage.spec = new ManagedSelectSpec();
            mviewctx.entitypagename = mviewctx.entity.concat(action);
            context.add(mviewctx.entitypagename, mviewctx.entitypage);
            mviewctx.entitypage.setEntity(mviewctx.entity);
        }
        
        if (mviewctx.custompage == null)
            mviewctx.custompage = new ManagedEditPage();
        
        for (String view : new String[] {
                mviewctx.createview,
                mviewctx.create1view,
                mviewctx.edit1view}) {
            if (view.equals(mviewctx.createview) &&
                    ((mviewctx.number == null) ||
                            (mviewctx.custompage.config != null)))
                continue;
            context.add(view, mviewctx.custompage);
            mviewctx.custompage.setEntity(mviewctx.entity);
        }

        if (mviewctx.displaypage == null)
            mviewctx.displaypage = new ManagedDisplayPage();
        
        context.add(mviewctx.display1view, mviewctx.displaypage);
        mviewctx.displaypage.setEntity(mviewctx.entity);
    }
    
}
