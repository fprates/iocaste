package org.iocaste.install.settings;

import org.iocaste.appbuilder.common.AbstractComponentData;
import org.iocaste.appbuilder.common.AbstractViewConfig;
import org.iocaste.appbuilder.common.PageBuilderContext;
import org.iocaste.appbuilder.common.dataformtool.DataFormToolItem;
import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DataType;
import org.iocaste.documents.common.DocumentModel;
import org.iocaste.documents.common.DocumentModelItem;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.Frame;

public class SettingsConfig extends AbstractViewConfig {
    private static final Object[][] ELEMENTS = {
            {"HOST", DataType.CHAR, 100, DataType.KEEPCASE},
            {"USERNAME", DataType.CHAR, 12, DataType.KEEPCASE},
            {"SECRET", DataType.CHAR, 24, DataType.KEEPCASE},
            {"DBNAME", DataType.CHAR, 12, DataType.UPPERCASE},
            {"OPTIONS", DataType.NUMC, 1, false}
    };
    
    @Override
    protected void execute(PageBuilderContext context) {
        DocumentModel model;
        DocumentModelItem item;
        DataElement element;
        AbstractComponentData tool;
        DataFormToolItem toolitem;
        Frame frame;
        
        context.view.setTitle("config");
        model = new DocumentModel("CONFIG_DATA");
        for (int i = 0; i < ELEMENTS.length; i++) {
            element = new DataElement((String)ELEMENTS[i][0]);
            element.setType((int)ELEMENTS[i][1]);
            element.setLength((int)ELEMENTS[i][2]);
            element.setUpcase((boolean)ELEMENTS[i][3]);
            item = new DocumentModelItem((String)ELEMENTS[i][0]);
            item.setDataElement(element);
            model.add(item);
        }
        
        tool = getTool("dbinfo");
        tool.custommodel = model;
        toolitem = tool.instance("HOST");
        toolitem.required = true;
        toolitem.focus = true;
        
        toolitem = tool.instance("USERNAME");
        toolitem.required = true;
        
        toolitem = tool.instance("SECRET");
        toolitem.secret = true;
        
        toolitem = tool.instance("OPTIONS");
        toolitem.componenttype = Const.LIST_BOX;
        
        frame = getElement("dbtypes");
        frame.setLegendStyle("dbtypes");
    }
    
}
