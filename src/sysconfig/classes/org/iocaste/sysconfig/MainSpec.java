package org.iocaste.sysconfig;

import org.iocaste.appbuilder.common.AbstractViewSpec;
import org.iocaste.appbuilder.common.PageBuilderContext;

public class MainSpec extends AbstractViewSpec {

    @Override
    protected void execute(PageBuilderContext context) {
        String tab;
        Context extcontext = getExtendedContext();
        
        tabbedpane(parent, "modules");
        for (String name : extcontext.modules.keySet()) {
            tab = name.concat("_tab");
            tabbedpaneitem("modules", tab);
            tabletool(tab, name);
        }
    }
}

