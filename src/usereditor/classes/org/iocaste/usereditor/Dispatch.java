package org.iocaste.usereditor;

import org.iocaste.appbuilder.common.AbstractActionHandler;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.documents.common.Query;
import org.iocaste.shell.common.Const;

public class Dispatch extends AbstractActionHandler {
    private String action;
    
    public Dispatch(String action) {
        this.action = action;
    }
    
    @Override
    protected void execute(PageBuilderContext context) throws Exception {
        Context extcontext;
        Query query;

        extcontext = getExtendedContext();
        extcontext.userdata.username = getdfst("selection", "USERNAME");
        extcontext.userdata.identity = getObject(
                "LOGIN", extcontext.userdata.username);
        if (extcontext.userdata.identity == null) {
            message(Const.ERROR, "invalid.user");
            return;
        }
        
        query = new Query();
        query.setModel("USER_AUTHORITY");
        query.andEqual("USERNAME", extcontext.userdata.username);
        extcontext.userdata.profiles = select(query);
        
        query = new Query();
        query.setModel("USER_TASKS_GROUPS");
        query.andEqual("USERNAME", extcontext.userdata.username);
        extcontext.userdata.tasks = select(query);
        
        init(action, getExtendedContext());
        redirect(action);
    }

}
