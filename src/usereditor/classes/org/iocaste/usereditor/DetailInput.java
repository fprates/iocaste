package org.iocaste.usereditor;

import org.iocaste.appbuilder.common.AbstractViewInput;
import org.iocaste.appbuilder.common.PageBuilderContext;

public class DetailInput extends AbstractViewInput {

    @Override
    protected void execute(PageBuilderContext context) {
        Context extcontext;
        
        extcontext = getExtendedContext();
        if (extcontext.userdata.identity != null) {
            dfset("identity", extcontext.userdata.identity);
            if (extcontext.extras != null)
                dfset("extras", extcontext.extras);
        } else {
            dfset("identity", "USERNAME", extcontext.userdata.username);
        }
        
        tableitemsadd("profiles", extcontext.userdata.profiles);
        tableitemsadd("tasks", extcontext.userdata.tasks);
    }

    @Override
    protected void init(PageBuilderContext context) {
        execute(context);
    }

}
