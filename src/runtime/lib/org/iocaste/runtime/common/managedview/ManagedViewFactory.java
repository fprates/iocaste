package org.iocaste.runtime.common.managedview;

import org.iocaste.runtime.common.application.Context;
import org.iocaste.runtime.common.managedview.create.ManagedCreateValidate;
import org.iocaste.runtime.common.managedview.display.ManagedDisplayPage;
import org.iocaste.runtime.common.managedview.edit.ManagedEditPage;

public class ManagedViewFactory {
    
    public final void build(Context context, ManagedViewContext mviewctx) {
        AbstractEntityPage entitypage;
        AbstractEntityCustomPage custompage;
        AbstractEntityDisplayPage displaypage;
        boolean create;
        
        mviewctx.redirect = (mviewctx.number == null)?
                mviewctx.create1view : mviewctx.edit1view;
        
        if (mviewctx.inputvalidate == null)
            mviewctx.inputvalidate = new ManagedInputValidate();
        
        if (mviewctx.save == null)
            mviewctx.save = new ManagedViewSave();
        
        if (mviewctx.updateload == null)
            mviewctx.updateload = new ManagedViewLoad();
        
        if (mviewctx.displayload == null)
            mviewctx.displayload = mviewctx.updateload;
        
        for (String action : new String[] {
                ManagedViewContext.CREATE,
                ManagedViewContext.DISPLAY,
                ManagedViewContext.EDIT}) {
            create = action.equals(ManagedViewContext.CREATE);
            if ((mviewctx.number != null) && create)
                continue;
            if (create)
                mviewctx.createvalidate = new ManagedCreateValidate();
            entitypage = new ManagedEntityPage();
            entitypage.action = action;
            mviewctx.entitypagename = mviewctx.entity.concat(action);
            context.add(mviewctx.entitypagename, entitypage);
            context.getPage(mviewctx.entitypagename).
                    setEntity(mviewctx.entity);
        }
        
        for (String view : new String[] {
                mviewctx.createview,
                mviewctx.create1view,
                mviewctx.edit1view}) {
            if (view.equals(mviewctx.createview) &&
                    (mviewctx.number == null))
                continue;
            custompage = new ManagedEditPage();
            context.add(view, custompage);
            context.getPage(view).setEntity(mviewctx.entity);
        }
        
        displaypage = new ManagedDisplayPage();
        context.add(mviewctx.display1view, displaypage);
        context.getPage(mviewctx.display1view).setEntity(mviewctx.entity);
    }
    
}
