package org.iocaste.install.settings;

import org.iocaste.appbuilder.common.AbstractViewSpec;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.kernel.common.DBNames;

public class SettingsSpec extends AbstractViewSpec {

    @Override
    protected void execute(PageBuilderContext context) {
        text(parent, "warning1");
        text(parent, "warning2");
        dataform(parent, "dbinfo");
        frame(parent, "dbtypes");
        radiogroup("dbtypes", "dbtype");
        for (String key : DBNames.names.keySet())
            radiobutton("dbtypes", "dbtype", key);
        button(parent, "continue");
    }
    
}

