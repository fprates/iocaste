package org.iocaste.workbench.project.viewer;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.appbuilder.common.AbstractActionHandler;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.workbench.AbstractCommand;
import org.iocaste.workbench.Context;

public class ViewerItemPick extends AbstractActionHandler {
    private ViewerItemPickData pickdata;
    
    public ViewerItemPick(ViewerItemPickData pickdata) {
        this.pickdata = pickdata;
    }
    
    @Override
    protected void execute(PageBuilderContext context) throws Exception {
        AbstractCommand handler;
        Map<String, Object> parameters;
        Context extcontext = getExtendedContext();

        pickdata.value = getinputst(pickdata.pickname);
        if (pickdata.command != null) {
            handler = context.getView("main").
                    getActionHandler(pickdata.command);
            parameters = new HashMap<>();
            parameters.put("name", pickdata.value);
            handler.set(parameters);
            handler.call(context);
        }
        
        if (pickdata.loader != null)
            pickdata.loader.execute(pickdata, extcontext);
        
        init(pickdata.redirect, extcontext);
        redirect(pickdata.redirect);
    }
    
}