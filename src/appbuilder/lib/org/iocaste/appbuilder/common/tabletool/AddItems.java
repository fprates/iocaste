package org.iocaste.appbuilder.common.tabletool;

public class AddItems extends AbstractTableHandler {

    public static void run(TableTool tabletool, TableToolData data) {
        Context context = new Context();
        
        context.data = data;
        additems(tabletool, context, context.data.getItems());
    }

}
