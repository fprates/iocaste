package org.iocaste.appbuilder.common;

import org.iocaste.shell.common.Element;
import org.iocaste.shell.common.NavControl;
import org.iocaste.shell.common.TabbedPane;
import org.iocaste.shell.common.TabbedPaneItem;

public abstract class AbstractViewConfig implements ViewConfig {
    private PageBuilderContext context;
    private NavControl navcontrol;
    
    /**
     * 
     * @param context
     */
    protected abstract void execute(PageBuilderContext context);
    
    /**
     * 
     * @param name
     * @return
     */
    protected final <T extends Element> T getElement(String name) {
        return context.view.getElement(name);
    }
    
    /**
     * 
     * @return
     */
    protected final NavControl getNavControl() {
        return navcontrol;
    }
    
    /**
     * 
     * @param tab
     * @param name
     * @return
     */
    protected final TabbedPaneItem getTabbedItem(String tab, String name) {
        return ((TabbedPane)getElement(tab)).get(name);
    }
    
    /*
     * (não-Javadoc)
     * @see org.iocaste.appbuilder.common.ViewConfig#run(
     *    org.iocaste.appbuilder.common.PageBuilderContext)
     */
    @Override
    public final void run(PageBuilderContext context) {
        this.context = context;
        execute(context);
    }
    
    /*
     * (não-Javadoc)
     * @see org.iocaste.appbuilder.common.ViewConfig#setNavControl(
     *    org.iocaste.shell.common.NavControl)
     */
    @Override
    public final void setNavControl(NavControl navcontrol) {
        this.navcontrol = navcontrol;
    }

}
