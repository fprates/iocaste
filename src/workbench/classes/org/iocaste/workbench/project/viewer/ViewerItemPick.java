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
        Context extcontext = getExtendedContext();
        String pickname = pickdata.items.concat(".NAME");
        String value = getinputst(pickname);
        AbstractCommand handler = context.getView("main").
                getActionHandler(pickdata.command);
        Map<String, String> parameters = new HashMap<>();
        
        parameters.put("name", value);
        handler.set(parameters);
        handler.call(context);
        
        if (pickdata.loader != null)
            pickdata.loader.execute(extcontext);
        
        init(pickdata.redirect, extcontext);
        redirect(pickdata.redirect);
    }
    
}