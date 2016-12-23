package org.iocaste.packagetool.main;

import org.iocaste.appbuilder.common.AbstractViewConfig;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.tabletool.TableTool;
import org.iocaste.appbuilder.common.tabletool.TableToolData;

public class MainConfig extends AbstractViewConfig {

    @Override
    protected void execute(PageBuilderContext context) {
        TableToolData tabletool;
        
        for (String name : new String[] {
                "inpackages",
                "unpackages",
                "erpackages"
                }) {
            tabletool = getTool(name);
            tabletool.mode = TableTool.DISPLAY;
            tabletool.model = "PACKAGE_GRID";
            tabletool.mark = true;
            tabletool.vlines = 0;
            tabletool.instance("EXCEPTION").length = 80;
        }
    }

}
