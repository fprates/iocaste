package org.iocaste.shell.common;

import java.util.ArrayList;
import java.util.List;

public class TabbedPane extends AbstractContainer {
    private static final long serialVersionUID = -8260508533459016709L;
    private List<TabbedPaneItem> itens;
    
    public TabbedPane(Container container, String name) {
        super(container, Const.TABBED_PANE, name);
        
        itens = new ArrayList<TabbedPaneItem>();
    }
    
    /*
     * (non-Javadoc)
     * @see org.iocaste.shell.common.AbstractContainer#add(
     *     org.iocaste.shell.common.Element)
     */
    @Override
    public final void add(Element element) { }

    /**
     * 
     * @param item
     */
    public final void add(TabbedPaneItem item) {
        itens.add(item);
    }
    
    /**
     * 
     * @return
     */
    public final TabbedPaneItem[] getItens() {
        return itens.toArray(new TabbedPaneItem[0]);
    }
}
