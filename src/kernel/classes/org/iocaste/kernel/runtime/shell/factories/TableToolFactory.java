package org.iocaste.kernel.runtime.shell.factories;

import java.util.HashMap;
import java.util.Map;

import org.iocaste.kernel.runtime.shell.ComponentEntry;
import org.iocaste.kernel.runtime.shell.ViewContext;
import org.iocaste.kernel.runtime.shell.tabletool.TableTool;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.Element;
import org.iocaste.shell.common.Table;
import org.iocaste.shell.common.TableColumn;
import org.iocaste.shell.common.tooldata.ViewSpecItem;

public class TableToolFactory extends AbstractSpecFactory {
    private Map<Const, ViewSpecItem.TYPES> types;
    
    public TableToolFactory() {
        types = new HashMap<>();
        types.put(Const.BUTTON, ViewSpecItem.TYPES.BUTTON);
        types.put(Const.CHECKBOX, ViewSpecItem.TYPES.CHECK_BOX);
        types.put(Const.LINK, ViewSpecItem.TYPES.LINK);
        types.put(Const.LIST_BOX, ViewSpecItem.TYPES.LISTBOX);
        types.put(Const.PARAMETER, ViewSpecItem.TYPES.PARAMETER);
        types.put(Const.TEXT, ViewSpecItem.TYPES.TEXT);
        types.put(Const.TEXT_FIELD, ViewSpecItem.TYPES.TEXT_FIELD);
    }
    
    @Override
    public final void addEventHandler(ViewContext viewctx, String htmlname) {
        Container table = viewctx.view.getElement(htmlname);
        for (Element element : table.getElements())
            if ((element.getType() == Const.TABLE_ITEM) && element.isVisible())
                setEvent(viewctx, table, element);
    }

    @Override
    public final void generate(
    		ViewContext viewctx, ComponentEntry entry, String prefix) {
        entry.component = new TableTool(viewctx, entry);
    }
    
    private final void setEvent(
            ViewContext viewctx, Container container, Element element) {
        TableColumn column;
        ViewSpecItem.TYPES type;
        Table table = (Table)container;
        Container item = (Container)element;
        boolean isseltype = (table.getSelectionType() != Table.MULTIPLE);
        
        for (Element child : item.getElements()) {
            column = table.getColumn(child.getName());
            if ((column == null) || !column.isVisible() || (column.isMark() &&
                    (!table.hasMark() || isseltype)))
                continue;
            type = types.get(child.getType());
            viewctx.factories.get(type).addEventHandler(
                    viewctx, child.getHtmlName());
        }
    }
}
