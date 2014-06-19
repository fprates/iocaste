package org.iocaste.appbuilder.common;

import org.iocaste.appbuilder.common.dashboard.DashboardComponent;
import org.iocaste.appbuilder.common.dashboard.DashboardFactory;
import org.iocaste.appbuilder.common.tabletool.TableTool;
import org.iocaste.docmanager.common.Manager;
import org.iocaste.shell.common.Element;
import org.iocaste.shell.common.TabbedPane;
import org.iocaste.shell.common.TabbedPaneItem;

public abstract class AbstractViewConfig implements ViewConfig {
    private PageBuilderContext context;
    private NavControl navcontrol;
    private Manager manager;
    
    /**
     * 
     * @param context
     */
    protected abstract void execute(PageBuilderContext context);
    
    /**
     * 
     * @return
     */
    @SuppressWarnings("unchecked")
    protected final <T extends ExtendedContext> T getExtendedContext() {
        return (T)context.getExtendedContext(context.view.getPageName());
    }
    
    /**
     * 
     * @param dashboard
     * @param name
     * @return
     */
    protected final DashboardComponent getDashboardItem(
            String dashboard, String name) {
        DashboardComponent component;
        DashboardFactory factory = getViewComponents().dashboards.
                get(dashboard);
        
        if (factory == null)
            throw new RuntimeException(
                    dashboard.concat(" is an invalid dashboard factory."));
        
        component = factory.get(name);
        if (component == null)
            throw new RuntimeException(
                    name.concat(" is an invalid dashboard component."));
        return component;
    }
    
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
    protected final Manager getManager() {
        return manager;
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
    
    /**
     * 
     * @param name
     * @return
     */
    protected final TableTool getTableTool(String name) {
        return getViewComponents().tabletools.get(name);
    }
    
    /**
     * 
     * @return
     */
    private final ViewComponents getViewComponents() {
        return context.getViewComponents(context.view.getPageName());
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
     * @see org.iocaste.appbuilder.common.ViewConfig#setManager(
     *    org.iocaste.docmanager.common.Manager)
     */
    @Override
    public final void setManager(Manager manager) {
        this.manager = manager;
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
