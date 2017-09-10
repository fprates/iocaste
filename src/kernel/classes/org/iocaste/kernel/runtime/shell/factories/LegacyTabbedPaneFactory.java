package org.iocaste.kernel.runtime.shell.factories;

import org.iocaste.kernel.runtime.shell.ViewContext;
import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.Container;
import org.iocaste.shell.common.Element;
import org.iocaste.shell.common.TabbedPane;

public class LegacyTabbedPaneFactory extends AbstractSpecFactory {

    @Override
    public final void addEventHandler(ViewContext viewctx, String htmlname) {
        String name;
        TabbedPane panes = viewctx.view.getElement(htmlname);
        for (Element element : panes.getElements())
            if (element.getType() == Const.TABBED_PANE_ITEM)
                viewctx.addEventHandler(
                    name = element.getHtmlName().concat("_bt"), name, "click");
    }

    @Override
    protected void execute(ViewContext viewctx,
    		Container container, String parent, String name) {
        new TabbedPane(container, name);
    }

}
