package org.iocaste.shell.common.tooldata;

import org.iocaste.shell.common.Const;
import org.iocaste.shell.common.Element;

/**
 * Componentes de abas.
 * 
 * @author francisco.prates
 *
 */
public class TabbedPane extends ToolDataElement {
    private static final long serialVersionUID = -8260508533459016709L;
    
    public TabbedPane(Context viewctx, String name) {
        super(viewctx, Const.TABBED_PANE, name);
        if (tooldata.style == null)
            tooldata.style = "tp_outer";
    }
    
    /*
     * (não-Javadoc)
     * @see org.iocaste.shell.common.AbstractContainer#add(
     *    org.iocaste.shell.common.Element)
     */
    @Override
    public final void add(Element element) {
        add(element, Const.TABBED_PANE_ITEM);
    }
    
    @Override
    public final boolean isContainable() {
        return true;
    }

    @Override
    public final boolean isMultipage() {
        return true;
    }
}
