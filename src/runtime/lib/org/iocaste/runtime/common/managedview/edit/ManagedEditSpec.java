package org.iocaste.runtime.common.managedview.edit;

import java.util.Map;

import org.iocaste.documents.common.ComplexModel;
import org.iocaste.documents.common.ComplexModelItem;
import org.iocaste.runtime.common.application.Context;
import org.iocaste.runtime.common.managedview.ManagedViewContext;
import org.iocaste.runtime.common.page.AbstractViewSpec;

public class ManagedEditSpec extends AbstractViewSpec {
    
    @Override
    public void execute(Context context) {
        ManagedViewContext mviewctx = context.mviewctx();
        ComplexModel model = context.runtime().
                getComplexModel(mviewctx.cmodel);
        
        dataform(parent, "head");
        tabbedpane(parent, "tabs");
        tabbedpaneitem("tabs", "basetab");
        dataform("basetab", "base");
        tabs(mviewctx, model.getItems());
    }

    protected void tabs(ManagedViewContext mviewctx,
            Map<String, ComplexModelItem> models) {
        String tablename;
        
        for (String name : models.keySet()) {
            if (models.get(name).model == null)
                continue;
            tabbedpaneitem("tabs", name);
            tablename = name.concat("_table");
            tabletool(name, tablename);
            mviewctx.models.put(tablename, name);
        }
    }
}
