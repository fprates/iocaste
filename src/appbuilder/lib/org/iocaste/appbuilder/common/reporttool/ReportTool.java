package org.iocaste.appbuilder.common.reporttool;

import java.util.Locale;

import org.iocaste.appbuilder.common.tabletool.TableTool;
import org.iocaste.appbuilder.common.tabletool.TableToolData;
import org.iocaste.documents.common.DataElement;
import org.iocaste.documents.common.DataType;
import org.iocaste.documents.common.ExtendedObject;
import org.iocaste.shell.common.CheckBox;
import org.iocaste.shell.common.Component;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.InputComponent;
import org.iocaste.shell.common.Shell;
import org.iocaste.shell.common.Table;
import org.iocaste.shell.common.TableColumn;
import org.iocaste.shell.common.TableItem;
import org.iocaste.shell.common.Text;

public class ReportTool {
    private Table table;
    private boolean translate;
    
    public ReportTool(ReportToolData data) {
        TableToolData ttdata;
        
        ttdata = new TableToolData();
        ttdata.context = data.context;
        ttdata.container = data.container;
        ttdata.name = data.name;
        ttdata.objects = data.objects;
        ttdata.model = data.model;
        ttdata.mode = TableTool.DISPLAY;
        
        new TableTool(ttdata);
    }
    
//    public final void setItens(ExtendedObject[] itens) {
//        DataElement element;
//        Component component;
//        InputComponent input;
//        String stext, name;
//        TableItem item;
//        TableColumn[] columns;
//        Locale locale;
//        Object value;
//        
//        if (itens == null || itens.length == 0)
//            return;
//
//        locale = table.getLocale();
//        table.importModel(itens[0].getModel());
//        columns = table.getColumns();
//        for (ExtendedObject oitem : itens) {
//            item = new TableItem(table);
//            for (TableColumn column : columns) {
//                if (column.isMark())
//                    continue;
//
//                name  = column.getName();
//                value = oitem.get(name);
//                element = column.getModelItem().getDataElement();
//                switch (element.getType()) {
//                case DataType.BOOLEAN:
//                    input = new CheckBox(item, name);
//                    input.set(value);
//                    input.setEnabled(false);
//                    break;
//                default:
//                    stext = Shell.toString(value, element, locale, false);
//                    component = new Text(item, name);
//                    component.setText(stext);
//                    component.setTranslatable(translate);
//                    break;
//                }
//            }
//        }
//    }
    
    public final void setTranslatable(boolean translate) {
        this.translate = translate;
    }
    
    public final void visible(String... columns) {
        for (TableColumn column : table.getColumns())
            column.setVisible(false);
        
        for (String column : columns)
            table.getColumn(column).setVisible(true);
    }
}
