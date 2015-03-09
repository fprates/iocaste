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
        
        for (String name : new String[] {
                "inpackages",
                "unpackages",
                "erpackages"
                }) {
            tabletool = getTableTool(name);
            tabletool.mode = TableTool.DISPLAY;
            tabletool.model = "PACKAGE_GRID";
            new TableToolColumn(tabletool, "EXCEPTION").size = 80;
            
            switch (name) {
            case "erpackages":
                tabletool.mark = false;
                break;
            default:
                tabletool.mark = true;
                break;
            }
        }
    }

}