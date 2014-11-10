package org.iocaste.appbuilder.common.tabletool;

public class AddItems extends AbstractTableHandler {

    public static void run(TableToolData data) {
        Context context = new Context();
        
        context.data = data;
        context.table = getTable(context.data);
        additems(context, context.data.objects);
    }

}
