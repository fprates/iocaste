package org.iocaste.appbuilder.common.factories;

import org.iocaste.appbuilder.common.tabletool.TableToolData;
import org.iocaste.shell.common.StandardContainer;

public class TableToolFactory extends AbstractSpecFactory {

    @Override
    protected void execute() {
        TableToolData ttdata;
        
        ttdata = new TableToolData();
        ttdata.context = context;
        ttdata.name = name;
        new StandardContainer(container, ttdata.name);
        components.add(ttdata);
    }

}
