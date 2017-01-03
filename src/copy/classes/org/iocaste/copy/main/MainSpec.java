package org.iocaste.copy.main;

import org.iocaste.appbuilder.common.AbstractViewSpec;
import org.iocaste.appbuilder.common.PageBuilderContext;

public class MainSpec extends AbstractViewSpec {

    @Override
    protected void execute(PageBuilderContext context) {
        dataform(parent, "model");
        radiogroup(parent, "grp");
        radiobutton(parent, "grp", "portsource");
        dataform(parent, "port");
        radiobutton(parent, "grp", "dbsource");
        dataform(parent, "db");
    }

}
