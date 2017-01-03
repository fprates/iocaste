package org.iocaste.copy.main;

import org.iocaste.appbuilder.common.AbstractActionHandler;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.shell.common.Const;

public class Copy extends AbstractActionHandler {

    @Override
    protected void execute(PageBuilderContext context) throws Exception {
        for (String key : new String[] {"portsource", "dbsource"}) {
            if (getinputbl(key)) {
                execute(key);
                break;
            }
        }
        
        message(Const.STATUS, "sucessful.copy");
    }

}
