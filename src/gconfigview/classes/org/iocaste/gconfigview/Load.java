package org.iocaste.gconfigview;

import org.iocaste.appbuilder.common.AbstractActionHandler;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.documents.common.Query;
import org.iocaste.shell.common.Const;

public class Load extends AbstractActionHandler {
    private String action;
    
    public Load(String action) {
        this.action = action;
    }
    
    @Override
    protected void execute(PageBuilderContext context) throws Exception {
        Query query;
        Context extcontext;
        
        extcontext = getExtendedContext();
        extcontext.appname = getdfst("package", "NAME");
        
        query = new Query();
        query.addColumns("GLOBAL_CONFIG_ITEM.ID",
                "GLOBAL_CONFIG_ITEM.NAME",
                "GLOBAL_CONFIG_ITEM.TYPE",
                "GLOBAL_CONFIG_VALUES.VALUE");
        query.setModel("GLOBAL_CONFIG_ITEM");
        query.join("GLOBAL_CONFIG_VALUES", "GLOBAL_CONFIG_ITEM.ID", "ID");
        query.andEqual("GLOBAL_CONFIG_ITEM.GLOBAL_CONFIG", extcontext.appname);
        
        extcontext.objects = select(query);
        if (extcontext.objects == null) {
            message(Const.STATUS, "no.config");
            return;
        }
        
        init(action, extcontext);
        redirect(action);
    }

}
