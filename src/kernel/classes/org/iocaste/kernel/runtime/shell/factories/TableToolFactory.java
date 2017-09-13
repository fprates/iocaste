package org.iocaste.kernel.runtime.shell.factories;

import org.iocaste.kernel.runtime.shell.ComponentEntry;
import org.iocaste.kernel.runtime.shell.ViewContext;
import org.iocaste.kernel.runtime.shell.tabletool.TableTool;
import org.iocaste.runtime.common.page.ViewSpecItem;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.Element;
import org.iocaste.shell.common.Table;

public class TableToolFactory extends AbstractSpecFactory {
    
    @Override
    public final void addEventHandler(ViewContext viewctx, String htmlname) {
        Table table = viewctx.view.getElement(htmlname);
        if (!table.hasMark() || table.getSelectionType() != Table.MULTIPLE)
            return;
        for (Element element : table.getElements())
            if ((element.getType() == Const.TABLE_ITEM) && element.isVisible())
                setMarkFocus(viewctx, element);
    }

    @Override
    public final void generate(
    		ViewContext viewctx, ComponentEntry entry, String prefix) {
        entry.component = new TableTool(viewctx, entry);
    }
    
    private final void setMarkFocus(ViewContext viewctx, Element element) {
        String htmlname = element.getHtmlName().concat(".mark");
        viewctx.factories.get(ViewSpecItem.TYPES.CHECK_BOX).
                addEventHandler(viewctx, htmlname);
    }
}
