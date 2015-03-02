package org.iocaste.packagetool;

import org.iocaste.appbuilder.common.AbstractViewConfig;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.tabletool.TableTool;
import org.iocaste.appbuilder.common.tabletool.TableToolColumn;
import org.iocaste.appbuilder.common.tabletool.TableToolData;

public class MainConfig extends AbstractViewConfig {

    @Override
    protected void execute(PageBuilderContext context) {
        TableToolData tabletool;
        String[] columns;
        
        columns = new String[] {"NAME"};
        for (String name : new String[] {
                "inpackages",
                "unpackages",
                "erpackages"
                }) {
            tabletool = getTableTool(name);
            tabletool.mode = TableTool.DISPLAY;
            tabletool.model = "PACKAGE_GRID";
            tabletool.show = columns;
            tabletool.mark = true;
            
            switch (name) {
            case "erpackages":
                new TableToolColumn(tabletool, "EXCEPTION").size = 80;
                break;
            }
        }
    }

}
